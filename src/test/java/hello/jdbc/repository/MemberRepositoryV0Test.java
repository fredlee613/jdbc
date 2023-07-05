package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {
        //save
        Member savedMember = repository.save(new Member("memberV5", 10000));

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