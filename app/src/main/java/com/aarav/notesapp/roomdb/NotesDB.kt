package com.aarav.notesapp.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Note::class, Category::class], version = 2)
abstract class NotesDB : RoomDatabase() {

    abstract val notesDAO: NoteDao
    abstract val categoryDAO: CategoryDao

    companion object {

        @Volatile
        private var INSTANCE: NotesDB? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS `categories_table` (" +
                            "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                            "`name` TEXT NOT NULL, " +
                            "`color` INTEGER NOT NULL)"
                )

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `notes_table_new` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                        `title` TEXT NOT NULL, 
                        `description` TEXT NOT NULL, 
                        `color` INTEGER NOT NULL, 
                        `category_id` INTEGER DEFAULT NULL, 
                        FOREIGN KEY(`category_id`) REFERENCES `categories_table`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL
                    )
                    """.trimIndent()
                )

                db.execSQL(
                    """
                    INSERT INTO `notes_table_new` (`id`, `title`, `description`, `color`, `category_id`)
                    SELECT `id`, `title`, `description`, `color`, NULL FROM `notes_table`
                    """.trimIndent()
                )

                db.execSQL("DROP TABLE `notes_table`")

                db.execSQL("ALTER TABLE `notes_table_new` RENAME TO `notes_table`")

                db.execSQL("CREATE INDEX IF NOT EXISTS `index_notes_table_category_id` ON `notes_table` (`category_id`)")
            }
        }

        fun getInstance(context: Context): NotesDB {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context = context,
                        NotesDB::class.java,
                        "motes_db"
                    )
                        .addMigrations(MIGRATION_1_2)
                        .build()
                }

                INSTANCE = instance
                return instance
            }
        }
    }
}