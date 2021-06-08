package com.example.slave3012314134124123.ratinglist

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

internal class RatingListViewModelTest {

    @Test
    fun `request to the server only once`() {
        runBlocking {
            val ratingList: RatingList = RatingList()
            val repository = mock<UserRepository> {
                onBlocking { getRatingList(any()) } doReturn Resource<RatingList>(ratingList,null)
            }
            val viewModel = RatingListViewModel(repository)
            viewModel.loadRatingListPaginated(TOKEN)
            verify(repository, times(1)).getRatingList(any())
        }
    }

    @Test
    fun `request not completed successfully`() {
        runBlocking {
            val repository = mock<UserRepository> {}
            val ratingList: RatingList = RatingList()
            whenever(repository.getRatingList(any())).thenReturn(Resource<RatingList>(ratingList, "Some error from server"))
            val viewModel = RatingListViewModel(repository)
            viewModel.loadRatingListPaginated(TOKEN)
            Assert.assertEquals("Произошла ошибка загрузки рейтинга",viewModel.result.message)
        }
    }

    @Test
    fun `request completed successfully`() {
        runBlocking {
            val repository = mock<UserRepository> {}
            val ratingList: RatingList = RatingList()
            whenever(repository.getRatingList(any())).thenReturn(Resource<RatingList>(ratingList, null))
            val viewModel = RatingListViewModel(repository)
            viewModel.loadRatingListPaginated(TOKEN)
            Assert.assertEquals("Загрузка рейтинга успешна",viewModel.result.message)
        }
    }
}