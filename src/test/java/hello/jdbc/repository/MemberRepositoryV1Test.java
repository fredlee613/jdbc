package hello.jdbc.repository;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberRepositoryV1Test {

    MemberRepositoryV1 repository;

    @BeforeEach
    void beforeEach() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        repository = new MemberRepositoryV1(dataSource);
    }

    @Test
    void crud() throws SQLException {
        //save
        Member savedMember = repository.save(new Member("memberV1", 10000));

        // find
        Member findMember = repository.findById(savedMember.getMemberId());
        assertThat(findMember).isEqualTo(savedMember);

        // update
        repository.update(savedMember.getMemberId(), 20000);
        assertThat(repository.findById(savedMember.getMemberId()).getMoney()).isEqualTo(20000);

        // delete
        repository.delete(savedMember.getMemberId());
        assertThatThrownBy(() -> repository.findById(savedMember.getMemberId())).isInstanceOf(NoSuchElementException.class);
    }

}