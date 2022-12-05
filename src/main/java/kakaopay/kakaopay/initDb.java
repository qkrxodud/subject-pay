package kakaopay.kakaopay;

import kakaopay.kakaopay.entitiy.Account;
import kakaopay.kakaopay.entitiy.AccountHistory;
import kakaopay.kakaopay.entitiy.Member;
import kakaopay.kakaopay.repository.AccountHistoryRepository;
import kakaopay.kakaopay.repository.AccountRepository;
import kakaopay.kakaopay.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;

/**
 * CSV 파일 읽고 DB 저장하기.
 */

@Component
@RequiredArgsConstructor
public class initDb {

    //초기 파일 갯수
    static final int initFileCount = 3;
    static String[] fileNameArr = new String[initFileCount];
    private final InitService initService;

    @PostConstruct
    public void init() throws IOException {
        // 파일리스트 초기화
        fileNameArr[0] = "member.csv";
        fileNameArr[1] = "account.csv";
        fileNameArr[2] = "account_history.csv";
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final MemberRepository memberRepository;
        private final AccountRepository accountRepository;
        private final AccountHistoryRepository accountHistoryRepository;

        public void dbInit() throws IOException {
            //파일리소스 셋팅
            for (int i = 0; i < initFileCount; i++) {
                String fileName = fileNameArr[i];

                ClassPathResource classPathResource = new ClassPathResource("/csv/" + fileName);
                List<List<String>> records = readFile(classPathResource); // 파일 갯수만큼 읽기

                for (int j = 0; j < records.size(); j++) {
                    // 첫번째 줄 제목은 삭제
                    if (isFirstLine(records.size(), j)) continue;
                    setData(fileName, records.get(j));
                }
            }
        }

        // 파일 읽기
        List<List<String>> readFile(ClassPathResource fileResource) throws IOException {
            BufferedReader br = null;
            List<List<String>> records = new ArrayList<>();

            try {
                ClassPathResource pathResource = fileResource;
                FileReader fileReader = new FileReader(pathResource.getURI().getPath());
                br = new BufferedReader(fileReader);
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    records.add(Arrays.asList(values));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                br.close(); // 사용중인 버퍼 초기화
            }

            return records;
        }

        //첫 번째 줄인지 체크
        boolean isFirstLine(int recordSize, int index) {
            boolean result = false;

            if (recordSize > 0 && index == 0) {
                result = true;
            }
            return result;
        }

        void setData(String fileName, List<String> records) {
            switch (fileName) {
                case "member.csv":
                    Member member = Member.initData(records);
                    memberRepository.save(member);
                    break;
                case "account.csv":
                    Long memberId = Long.parseLong(records.get(0).trim());
                    Optional<Member> findMember = memberRepository.findById(memberId);
                    Account account = Account.initData(records, findMember.get());
                    accountRepository.save(account);
                    break;
                case "account_history.csv":
                    Optional<Account> accountNos = accountRepository.findById(records.get(0).trim());
                    AccountHistory accountHistory = AccountHistory.initData(records, accountNos.get());
                    accountHistoryRepository.save(accountHistory);
                    break;

            }

        }


    }

}
