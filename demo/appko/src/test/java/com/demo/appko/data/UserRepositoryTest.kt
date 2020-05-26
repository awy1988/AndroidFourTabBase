package com.demo.appko.data

import com.demo.appko.data.remote.api.AuthService
import com.demo.corelib.network.base.HandleResponseHeaderRequestCallbackListener
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class UserRepositoryTest {
    private lateinit var userRepository : UserRepository
    private lateinit var authService: AuthService
    private lateinit var listener : HandleResponseHeaderRequestCallbackListener<Any>
    @Before
    fun setUp() {
        authService = mock()
        listener = mock()
        userRepository = UserRepository(authService)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun test_login() {
        userRepository.login("username", "password", "1234","encryptedData", listener)
        verify(authService).authorizations("username", "password", "1234", "encryptedData",listener)
    }
}
