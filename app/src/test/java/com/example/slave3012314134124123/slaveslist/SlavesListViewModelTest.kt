package com.example.slave3012314134124123.slaveslist

import com.example.slave3012314134124123.data.remote.responses.SlavesList
import com.example.slave3012314134124123.data.remote.responses.User
import com.example.slave3012314134124123.repository.UserRepository
import com.example.slave3012314134124123.user.UserViewModel
import com.example.slave3012314134124123.util.Constants.TOKEN
import com.example.slave3012314134124123.util.Resource
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.*

internal class SlavesListViewModelTest {



    @Test
    fun `request to the server only once`() {
        runBlocking {
            val slavesList: SlavesList = SlavesList()
            val repository = mock<UserRepository> {
                onBlocking { getSlavesList(any()) } doReturn Resource<SlavesList>(slavesList,null)
            }
            val viewModel = SlavesListViewModel(repository)
            viewModel.loadSlavesPaginated(TOKEN)
            verify(repository, times(1)).getSlavesList(any())
        }
    }

    @Test
    fun `request not completed successfully`() {
        runBlocking {
            val repository = mock<UserRepository> {}
            val slavesList: SlavesList = SlavesList()
            whenever(repository.getSlavesList(any())).thenReturn(Resource<SlavesList>(slavesList, "Some error from server"))
            val viewModel = SlavesListViewModel(repository)
            viewModel.loadSlavesPaginated(TOKEN)
            Assert.assertEquals("Произошла ошибка загрузки списка рабов",viewModel.result.message)
        }
    }

    @Test
    fun `request completed successfully`() {
        runBlocking {
            val repository = mock<UserRepository> {}
            val slavesList: SlavesList = SlavesList()
            whenever(repository.getSlavesList(any())).thenReturn(Resource<SlavesList>(slavesList, null))
            val viewModel = SlavesListViewModel(repository)
            viewModel.loadSlavesPaginated(TOKEN)
            Assert.assertEquals("Загрузка списка рабов успешна",viewModel.result.message)
        }
    }
}