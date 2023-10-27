package uz.instat.tasklist.busines.cache.dao

import androidx.room.*
import uz.instat.tasklist.busines.cache.data.TaskCache

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskCache): Long

    @Update
    suspend fun updateTask(task: TaskCache): Unit

    @Query("select * from task")
    suspend fun getAllTasks(): List<TaskCache>

    @Query("select * from task where status == 1")
    suspend fun getInProgressTasks(): List<TaskCache>

    @Query("select * from task where status == 2")
    suspend fun getFinishTasks(): List<TaskCache>

    @Query("update task set status = 2 where _id=:id")
    suspend fun finishTask(id: Long): Unit

    @Query("update task set status = 1 where _id=:id")
    suspend fun inProgressTask(id: Long): Unit

    @Query("update task set status = 0 where _id=:id")
    suspend fun createTask(id: Long): Unit

    @Query("select * from task where _id=:id")
    suspend fun getTask(id: Long): TaskCache

    @Query("delete from task  where _id=:id")
    suspend fun deleteTask(id: Long):Unit

}