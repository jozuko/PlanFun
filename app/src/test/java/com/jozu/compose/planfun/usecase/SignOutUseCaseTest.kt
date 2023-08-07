package com.jozu.compose.planfun.usecase

import com.jozu.compose.planfun.domain.account.AccountRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SignOutUseCaseTest {
    @Spy
    lateinit var accountRepository: AccountRepository

    @InjectMocks
    lateinit var target: SignOutUseCase

    private var closable: AutoCloseable? = null

    @Before
    fun before() {
        closable = MockitoAnnotations.openMocks(this)
    }

    @After
    fun after() {
        closable?.close()
    }

    @Test
    fun `sign out`() = runBlocking {
        whenever(accountRepository.signOut())
            .thenReturn(Unit)
            .thenThrow(RuntimeException("Foo"))

        assertEquals(Unit, target.signOut())
        try {
            target.signOut()
            assert(false)
        } catch (error: Throwable) {
            assertEquals("Foo", error.message)
        }
    }
}