package com.example.praticetokensecurity.domain.user;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.praticetokensecurity.common.code.ErrorStatus;
import com.example.praticetokensecurity.common.error.ApiException;
import com.example.praticetokensecurity.domain.book.dto.responseDto.RentedBookResponseDto;
import com.example.praticetokensecurity.domain.book.entity.Book;
import com.example.praticetokensecurity.domain.book.enums.BookStatus;
import com.example.praticetokensecurity.domain.book.repository.BookRepository;
import com.example.praticetokensecurity.domain.like.dto.response.LikedResponseDto;
import com.example.praticetokensecurity.domain.like.entity.Like;
import com.example.praticetokensecurity.domain.like.repository.LikeRepository;
import com.example.praticetokensecurity.domain.user.dto.request.UserDeleteRequestDto;
import com.example.praticetokensecurity.domain.user.dto.request.UserUpdateRequestDto;
import com.example.praticetokensecurity.domain.user.dto.response.UserResponseDto;
import com.example.praticetokensecurity.domain.user.entity.CustomUserPrincipal;
import com.example.praticetokensecurity.domain.user.entity.User;
import com.example.praticetokensecurity.domain.user.enums.UserRole;
import com.example.praticetokensecurity.domain.user.repository.UserRepository;
import com.example.praticetokensecurity.domain.user.service.UserService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private LikeRepository likeRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void 유저_정보_조회() {
        Long userId = 1L;
        User user = new User("test@gmail.com", "1234", UserRole.USER, "테스트", "010-7777-7777");
        ReflectionTestUtils.setField(user, "id", userId);
        CustomUserPrincipal authUser = new CustomUserPrincipal(user);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserResponseDto response = userService.findOne(authUser);

        assertEquals("test@gmail.com", response.getEmail());
        assertEquals("USER", response.getUserRole());
        assertEquals("테스트", response.getUserName());
        assertEquals("010-7777-7777", response.getPhoneNum());
    }

    @Test
    void 유저_정보_수정_성공() {
        Long userId = 1L;
        User user = new User("test@gmail.com", "encoded1234", UserRole.USER, "테스트",
            "010-7777-7777");
        ReflectionTestUtils.setField(user, "id", userId);
        CustomUserPrincipal authUser = new CustomUserPrincipal(user);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("1234", "encoded1234")).thenReturn(true);
        when(passwordEncoder.encode("newpass")).thenReturn("encodedNewpass");

        UserUpdateRequestDto updateRequestDto = new UserUpdateRequestDto("테스트2", "1234",
            "010-8888-8888", "newpass");

        Assertions.assertDoesNotThrow(() -> userService.updateUser(authUser, updateRequestDto));

    }

    @Test
    void 비밀번호_불일치() {
        Long userId = 1L;
        User user = new User("test@gmail.com", "encoded1234", UserRole.USER, "테스트",
            "010-7777-7777");

        ReflectionTestUtils.setField(user, "id", userId);
        CustomUserPrincipal authUser = new CustomUserPrincipal(user);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongpass", "encoded1234")).thenReturn(false);

        UserUpdateRequestDto updateRequestDto = new UserUpdateRequestDto("테스트2", "wrongpass",
            "010-8888-8888", "newpass");

        ApiException exception = assertThrows(ApiException.class,
            () -> userService.updateUser(authUser, updateRequestDto));
        assertEquals(ErrorStatus.PASSWORD_NOT_MATCH, exception.getErrorCode());

    }

    @Test
    void 회원_탈퇴_성공() {
        Long userId = 1L;
        User user = new User("test@gmail.com", "encoded1234", UserRole.USER, "테스트",
            "010-7777-7777");
        Long bookId = 1L;
        Book book = spy(new Book("책 테스트", "테스터", "레전드", BookStatus.RENTED));

        ReflectionTestUtils.setField(user, "id", userId);
        ReflectionTestUtils.setField(book, "id", bookId);
        book.assignUser(user);

        CustomUserPrincipal authUser = new CustomUserPrincipal(user);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("1234", "encoded1234")).thenReturn(true);
        when(bookRepository.findAllByUserIdAndBookStatus(userId, BookStatus.RENTED)).thenReturn(
            List.of(book));

        UserDeleteRequestDto deleteRequestDto = new UserDeleteRequestDto("1234");

        assertDoesNotThrow(() -> userService.deleteUser(authUser, deleteRequestDto));
        verify(book, times(1)).itsNotYourBook();
        assertEquals(BookStatus.AVAILABLE, book.getBookStatus());
        assertTrue(user.isDeleted());

    }


    @Test
    void 회원_탈퇴_시_비밀번호_불일치() {
        Long userId = 1L;
        User user = new User("test@gmail.com", "encoded1234", UserRole.USER, "테스트",
            "010-7777-7777");
        Long bookId = 1L;
        Book book = spy(new Book("책 테스트", "테스터", "레전드", BookStatus.RENTED));

        ReflectionTestUtils.setField(user, "id", userId);
        ReflectionTestUtils.setField(book, "id", bookId);
        book.assignUser(user);

        CustomUserPrincipal authUser = new CustomUserPrincipal(user);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongpass", "encoded1234")).thenReturn(false);

        UserDeleteRequestDto deleteRequestDto = new UserDeleteRequestDto("wrongpass");

        ApiException exception = assertThrows(ApiException.class,
            () -> userService.deleteUser(authUser, deleteRequestDto));
        assertEquals(ErrorStatus.PASSWORD_NOT_MATCH, exception.getErrorCode());

    }

    @Test
    void 대여한_책_목록_조회() {
        Long userId = 1L;
        User user = new User("test@gmail.com", "encoded1234", UserRole.USER, "테스트",
            "010-7777-7777");

        Book book1 = new Book("책 테스트1", "테스터1", "레전드1", BookStatus.RENTED);
        Book book2 = new Book("책 테스트2", "테스터2", "레전드2", BookStatus.RENTED);
        Book book3 = new Book("책 테스트3", "테스터3", "레전드3", BookStatus.RENTED);

        ReflectionTestUtils.setField(user, "id", userId);
        ReflectionTestUtils.setField(book1, "id", 1L);
        ReflectionTestUtils.setField(book2, "id", 2L);
        ReflectionTestUtils.setField(book3, "id", 3L);

        book1.assignUser(user);
        book2.assignUser(user);
        book3.assignUser(user);

        List<Book> books = List.of(book1, book2, book3);

        Pageable pageable = mock(Pageable.class);
        Page<Book> bookPage = new PageImpl<>(books);
        when(bookRepository.findByUserIdAndBookStatus(userId, BookStatus.RENTED,
            pageable)).thenReturn(bookPage);

        Page<RentedBookResponseDto> response = userService.getMyRentBook(userId, pageable);

        assertEquals(3, response.getTotalElements());

        List<String> titles = response.getContent().stream()
            .map(RentedBookResponseDto::getTitle)
            .toList();

        assertTrue(titles.contains("책 테스트1"));
        assertTrue(titles.contains("책 테스트2"));
        assertTrue(titles.contains("책 테스트3"));

        // 전체 목록 출력 (확인용)
        response.getContent().forEach(System.out::println);

    }

    @Test
    void 좋아요한_책_목록_조회() {
        Long userId = 1L;
        User user = new User("test@gmail.com", "encoded1234", UserRole.USER, "테스트",
            "010-7777-7777");

        Book book1 = new Book("책 테스트1", "테스터1", "레전드1", BookStatus.RENTED);
        Book book2 = new Book("책 테스트2", "테스터2", "레전드2", BookStatus.RENTED);
        Book book3 = new Book("책 테스트3", "테스터3", "레전드3", BookStatus.RENTED);

        ReflectionTestUtils.setField(user, "id", userId);
        ReflectionTestUtils.setField(book1, "id", 1L);
        ReflectionTestUtils.setField(book3, "id", 3L);

        book1.assignUser(user);
        book2.assignUser(user);
        book3.assignUser(user);

        List<Book> books = List.of(book1, book2, book3);

        Pageable pageable = mock(Pageable.class);
        // Create Like entities for the user and books
        Page<Like> likePage = new PageImpl<>(List.of(
            new com.example.praticetokensecurity.domain.like.entity.Like(user, book1, true),
            new com.example.praticetokensecurity.domain.like.entity.Like(user, book3, true)
        ));
        when(likeRepository.findByUserId(userId, pageable)).thenReturn(likePage);

        Page<LikedResponseDto> response = userService.getMyLikedBook(userId, pageable);

        assertEquals(2, response.getTotalElements());

        List<String> titles = response.getContent().stream()
            .map(LikedResponseDto::getTitle)
            .toList();

        assertTrue(titles.contains("책 테스트1"));
        assertTrue(titles.contains("책 테스트3"));

        // 전체 목록 출력 (확인용)
        response.getContent().forEach(System.out::println);

    }


}
