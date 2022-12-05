package kakaopay.kakaopay.service;

import kakaopay.kakaopay.entitiy.Account;
import kakaopay.kakaopay.entitiy.Member;
import kakaopay.kakaopay.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static kakaopay.kakaopay.entitiy.Account.createAccount;
import static kakaopay.kakaopay.entitiy.Account.createAccountNo;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

    private final AccountRepository accountRepository;

    //계좌 가입
    @Transactional
    public Account join(Member member) {
        int bankCode = 1000;
        String maxvalue = accountRepository.accountMaxValue();
        String accountNo = createAccountNo(maxvalue, bankCode);

        Account saveAccount = accountRepository.save(createAccount(accountNo, member));
        return saveAccount;
    }

    //전체 계좌 조회
    public List<Account> findAccounts() {
        return accountRepository.findAll();
    }

    //계좌 번호로 계좌 조회
    public Optional<Account> findByAccount(String accountNo) {
        return accountRepository.findById(accountNo);
    }

    //멤버 번호로 계좌 조회
    public List<Account> findByMemberId(Long memberId) {
        return accountRepository.findByMember_MemberId(memberId);
    }

    //예치금 조회
    public List<Object[]> findDepositAmounts(int memberId) {
        return accountRepository.findDepositAmounts(memberId);
    }


}
