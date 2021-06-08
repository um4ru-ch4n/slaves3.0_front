package com.example.slave3012314134124123.friendslist

import com.example.slave3012314134124123.data.remote.responses.FriendsList
import com.example.slave3012314134124123.data.remote.responses.RatingList
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

internal class FriendsListViewModelTest {


    @Test
    fun `request to the server only once`() {
        runBlocking {
            val friendsList: FriendsList = FriendsList()
            val repository = mock<UserRepository> {
                onBlocking { getFriendsList(any()) } doReturn Resource<FriendsList>(friendsList,null)
            }
            val viewModel = FriendsListViewModel(repository)
            viewModel.loadFriendsPaginated(TOKEN)
            verify(repository, times(1)).getFriendsList(any())
        }
    }

    @Test
    fun `request not completed successfully`() {
        runBlocking {
            val repository = mock<UserRepository> {}
            val friendsList: FriendsList = FriendsList()
            whenever(repository.getFriendsList(any())).thenReturn(Resource<FriendsList>(friendsList, "Some error from server"))
            val viewModel = FriendsListViewModel(repository)
            viewModel.loadFriendsPaginated(TOKEN)
            Assert.assertEquals("Произошла ошибка загрузки списка друзей",viewModel.result.message)
        }
    }

    @Test
    fun `request completed successfully`() {
        runBlocking {
            val repository = mock<UserRepository> {}
            val friendsList: FriendsList = FriendsList()
            whenever(repository.getFriendsList(any())).thenReturn(Resource<FriendsList>(friendsList, null))
            val viewModel = FriendsListViewModel(repository)
            viewModel.loadFriendsPaginated(TOKEN)
            Assert.assertEquals("Загрузка списка друзей успешна",viewModel.result.message)
        }
    }
}