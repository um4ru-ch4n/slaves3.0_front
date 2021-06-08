package com.example.slave3012314134124123.fellowslaves

import com.example.slave3012314134124123.data.remote.responses.FellowSlaves
import com.example.slave3012314134124123.data.remote.responses.FriendsList
import com.example.slave3012314134124123.friendslist.FriendsListViewModel
import com.example.slave3012314134124123.repository.UserRepository
import com.example.slave3012314134124123.util.Constants
import com.example.slave3012314134124123.util.Resource
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.junit.Assert
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.kotlin.*

internal class FellowSlavesViewModelTest {


    @Test
    fun `request to the server only once`() {
        runBlocking {
            val fellowSlaves: FellowSlaves = FellowSlaves()
            val repository = mock<UserRepository> {
                onBlocking { postGetFellowSlaves(any(), any()) } doReturn Resource<FellowSlaves>(fellowSlaves,null)
            }
            val buuBodyRequest = mock<RequestBody>{}
            val viewModel = FellowSlavesViewModel(repository)
            viewModel.loadFellowSlavesPaginated(Constants.TOKEN, buuBodyRequest)
            verify(repository, times(1)).postGetFellowSlaves(any(),any())
        }
    }

    @Test
    fun `request not completed successfully`() {
        runBlocking {
            val repository = mock<UserRepository> {}
            val fellowSlaves: FellowSlaves = FellowSlaves()
            whenever(repository.postGetFellowSlaves(any(),any())).thenReturn(Resource<FellowSlaves>(fellowSlaves, "Some error from server"))
            val buuBodyRequest = mock<RequestBody>{}
            val viewModel = FellowSlavesViewModel(repository)
            viewModel.loadFellowSlavesPaginated(Constants.TOKEN, buuBodyRequest)
            Assert.assertEquals("Произошла ошибка загрузки списка рабов",viewModel.result.message)
        }
    }

    @Test
    fun `request completed successfully`() {
        runBlocking {
            val repository = mock<UserRepository> {}
            val fellowSlaves: FellowSlaves = FellowSlaves()
            whenever(repository.postGetFellowSlaves(any(),any())).thenReturn(Resource<FellowSlaves>(fellowSlaves, null))
            val buuBodyRequest = mock<RequestBody>{}
            val viewModel = FellowSlavesViewModel(repository)
            viewModel.loadFellowSlavesPaginated(Constants.TOKEN, buuBodyRequest)
            Assert.assertEquals("Загрузка списка рабов успешна",viewModel.result.message)
        }
    }
}