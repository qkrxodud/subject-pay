package kakaopay.kakaopay.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import static kakaopay.kakaopay.entitiy.QAccount.account;

@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepositoryCustom {

    @Autowired
    private final JPAQueryFactory queryFactory;

    @Override
    public String accountMaxValue() {

        return queryFactory
                .select(account.accountNo.max())
                .from(account).fetchOne();
    }

}
