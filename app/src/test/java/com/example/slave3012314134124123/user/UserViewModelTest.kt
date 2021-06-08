package com.example.slave3012314134124123.user


import com.example.slave3012314134124123.data.remote.responses.User
import com.example.slave3012314134124123.repository.UserRepository
import com.example.slave3012314134124123.util.Constants.TOKEN
import com.example.slave3012314134124123.util.Resource
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.*

class UserViewModelTest {

    @Test
    fun `request to the server only once`() {
        runBlocking {
            val user:User = User(0,0,0,0,0,0,0,"","","",0,false,0,0,0,"","","",0,0,0,"",0,0,0,0,0,0,0,"")
            val repository = mock<UserRepository> {
                onBlocking { getUser(any()) } doReturn Resource<User>(user,null)
            }
            val viewModel = UserViewModel(repository)
            viewModel.loadUser(TOKEN)
            verify(repository, times(1)).getUser(any())
        }
    }

    @Test
    fun `authorization error`() {
        runBlocking {
            val repository = mock<UserRepository> {}
            val user:User = User(0,0,0,0,0,0,0,"","","",0,false,0,0,0,"","","",0,0,0,"",0,0,0,0,0,0,0,"")

            whenever(repository.getUser(any())).thenReturn(Resource<User>(user,"HTTP 401 Unauthorized"))

            val viewModel = UserViewModel(repository)
            val result = viewModel.loadUser("1234567890")

            Assert.assertEquals("Ошибка авторизации",result.message)
        }
    }

    @Test
    fun `authorization successful`() {
        runBlocking {
            val repository = mock<UserRepository> {}
            val user:User = User(0,0,0,0,0,0,0,"","","",0,false,0,0,0,"","","",0,0,0,"",0,0,0,0,0,0,0,"")

            whenever(repository.getUser(any())).thenReturn(Resource<User>(user,null))

            val viewModel = UserViewModel(repository)
            val result = viewModel.loadUser(TOKEN)

            Assert.assertEquals("Авторизация успешна",result.message)
        }
    }

}
