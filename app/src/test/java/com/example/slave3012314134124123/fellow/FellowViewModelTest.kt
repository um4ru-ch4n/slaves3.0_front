package com.example.slave3012314134124123.fellow

import com.example.slave3012314134124123.data.remote.responses.Fellow
import com.example.slave3012314134124123.repository.UserRepository
import com.example.slave3012314134124123.util.Constants
import com.example.slave3012314134124123.util.Resource
import kotlinx.coroutines.runBlocking
import okhttp3.RequestBody
import org.junit.Assert

import org.junit.Test
import org.mockito.kotlin.*

internal class FellowViewModelTest {

    @Test
    fun `request to the server only once (loadFellow)`() {
        runBlocking {
            //val fellow: Fellow = null!!
            val repository = mock<UserRepository> {
                onBlocking { postFellow(any(), any()) } doReturn Resource<Fellow>(null,null)
            }
            val buuBodyRequest = mock<RequestBody>{}
            val viewModel = FellowViewModel(repository)
            viewModel.loadFellow(Constants.TOKEN, buuBodyRequest)
            verify(repository, times(1)).postFellow(any(), any())
        }
    }

    @Test
    fun `request not completed successfully (loadFellow)`() {
        runBlocking {
            val repository = mock<UserRepository> {}
            whenever(repository.postFellow(any(), any())).thenReturn(Resource<Fellow>(null, "Some error from server"))
            val buuBodyRequest = mock<RequestBody>{}
            val viewModel = FellowViewModel(repository)
            val result = viewModel.loadFellow(Constants.TOKEN, buuBodyRequest)
            Assert.assertEquals("Произошла ошибка загрузки профиля пользователя",result.message)
        }
    }

    @Test
    fun `request completed successfully (loadFellow)`() {
        runBlocking {
            val repository = mock<UserRepository> {}
            whenever(repository.postFellow(any(), any())).thenReturn(Resource<Fellow>(null, null))
            val buuBodyRequest = mock<RequestBody>{}
            val viewModel = FellowViewModel(repository)
            val result = viewModel.loadFellow(Constants.TOKEN, buuBodyRequest)
            Assert.assertEquals("Загрузка профиля пользователя успешна", result.message)
        }
    }


    @Test
    fun `request not completed successfully (buyFellow)`() {
        runBlocking {
            val repository = mock<UserRepository> {}
            whenever(repository.postBuy(any(), any())).thenReturn(Resource<String>(null, "Some error from server"))
            val buuBodyRequest = mock<RequestBody>{}
            val viewModel = FellowViewModel(repository)
            val result = viewModel.buyFellow(Constants.TOKEN, buuBodyRequest)
            Assert.assertEquals("В покупке отказано",result.message)
        }
    }

    @Test
    fun `request completed successfully (buyFellow)`() {
        runBlocking {
            val repository = mock<UserRepository> {}
            whenever(repository.postBuy(any(), any())).thenReturn(Resource<String>(null, null))
            val buuBodyRequest = mock<RequestBody>{}
            val viewModel = FellowViewModel(repository)
            val result = viewModel.buyFellow(Constants.TOKEN, buuBodyRequest)
            Assert.assertEquals("Покупка прошла успешно", result.message)
        }
    }




    @Test
    fun `request not completed successfully (saleFellow)`() {
        runBlocking {
            val repository = mock<UserRepository> {}
            whenever(repository.postSale(any(), any())).thenReturn(Resource<String>(null, "Some error from server"))
            val buuBodyRequest = mock<RequestBody>{}
            val viewModel = FellowViewModel(repository)
            val result = viewModel.saleFellow(Constants.TOKEN, buuBodyRequest)
            Assert.assertEquals("Ошибка продажи",result.message)
        }
    }

    @Test
    fun `request completed successfully (saleFellow)`() {
        runBlocking {
            val repository = mock<UserRepository> {}
            whenever(repository.postSale(any(), any())).thenReturn(Resource<String>(null, null))
            val buuBodyRequest = mock<RequestBody>{}
            val viewModel = FellowViewModel(repository)
            val result = viewModel.saleFellow(Constants.TOKEN, buuBodyRequest)
            Assert.assertEquals("Продажа прошла успешно", result.message)
        }
    }

    @Test
    fun `request not completed successfully (setJob)`() {
        runBlocking {
            val repository = mock<UserRepository> {}
            whenever(repository.postSetJob(any(), any())).thenReturn(Resource<String>(null, "Some error from server"))
            val buuBodyRequest = mock<RequestBody>{}
            val viewModel = FellowViewModel(repository)
            val result = viewModel.setJob(Constants.TOKEN, buuBodyRequest)
            Assert.assertEquals("Ошибка назначения работы",result.message)
        }
    }

    @Test
    fun `request completed successfully (setJob)`() {
        runBlocking {
            val repository = mock<UserRepository> {}
            whenever(repository.postSetJob(any(), any())).thenReturn(Resource<String>(null, null))
            val buuBodyRequest = mock<RequestBody>{}
            val viewModel = FellowViewModel(repository)
            val result = viewModel.setJob(Constants.TOKEN, buuBodyRequest)
            Assert.assertEquals("Работа назначена", result.message)
        }
    }


    @Test
    fun `request not completed successfully (setFetter)`() {
        runBlocking {
            val repository = mock<UserRepository> {}
            whenever(repository.postSetFetter(any(), any())).thenReturn(Resource<String>(null, "Some error from server"))
            val buuBodyRequest = mock<RequestBody>{}
            val viewModel = FellowViewModel(repository)
            val result = viewModel.setFetter(Constants.TOKEN, buuBodyRequest)
            Assert.assertEquals("Ошибка уставноки цепей",result.message)
        }
    }

    @Test
    fun `request completed successfully (setFetter)`() {
        runBlocking {
            val repository = mock<UserRepository> {}
            whenever(repository.postSetFetter(any(), any())).thenReturn(Resource<String>(null, null))
            val buuBodyRequest = mock<RequestBody>{}
            val viewModel = FellowViewModel(repository)
            val result = viewModel.setFetter(Constants.TOKEN, buuBodyRequest)
            Assert.assertEquals("Цепи установлены", result.message)
        }
    }


}