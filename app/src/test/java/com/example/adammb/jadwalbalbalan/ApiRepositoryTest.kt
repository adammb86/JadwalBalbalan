package com.example.adammb.jadwalbalbalan

import com.example.adammb.jadwalbalbalan.api.ApiRepository
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class ApiRepositoryTest {

    @Test
    fun doRequest() {
        val apiRepository = mock(ApiRepository::class.java)

        // URL Next Event
        val url = BuildConfig.BASE_URL + "/api/v1/json/" + BuildConfig.TSDB_API_KEY + "eventsnextleague.php?id=4328"

        apiRepository.doRequest(url)
        verify(apiRepository).doRequest(url)
    }
}