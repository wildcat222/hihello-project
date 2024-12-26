package spring.hi_hello_spring.employee.command.application.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;
import spring.hi_hello_spring.common.util.RedisService;
import spring.hi_hello_spring.employee.command.application.dto.employee.ModifyPasswordReqDTO;
import spring.hi_hello_spring.employee.command.domain.aggregate.entity.Employee;
import spring.hi_hello_spring.employee.command.domain.repository.EmployeeRepository;

import java.security.Key;

import static spring.hi_hello_spring.common.util.CustomUserUtils.getCurrentEmployeeSeq;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RedisService redisService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    private Key key;
    @Value("${token.secret}")
    private String secretKey;

    @Transactional
    public void logout(String accessToken) {

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        key = Keys.hmacShaKeyFor(keyBytes);
        String EmployeeSeq = String.valueOf(getCurrentEmployeeSeq());
        accessToken = accessToken.substring(7);

        redisService.deleteToken(EmployeeSeq);

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();

        // 만료 시간 (exp는 Unix timestamp 형식)
        long exp = claims.getExpiration().getTime() / 1000;  // 초 단위로 변환

        // 현재 시간과 만료 시간을 비교하여 TTL 계산
        long ttl = exp - (System.currentTimeMillis() / 1000);

        redisService.saveToken(EmployeeSeq + "a", accessToken, ttl);

    }

    @Transactional
    public void modifyPwd(Long employeeSeq, ModifyPasswordReqDTO modifyPwdDTO) {

        Employee employee = employeeRepository.findByEmployeeSeq(employeeSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.USER_NOT_FOUND));

        // 입력한 새 비밀번호와 확인용 새 비밀번호 일치 확인
        if (!modifyPwdDTO.getEmployeeNewPwd().equals(modifyPwdDTO.getCheckEmployeeNewPwd())) {
            // 새 비밀번호를 다시 확인해주세요
            throw new CustomException(ErrorCodeType.NEW_PWD_MISMATCH);
        }
        // 기존 비밀번호와 DB내 비밀번호가 일치하는지 확인
        else if (!passwordEncoder.matches(modifyPwdDTO.getEmployeePassword(), employee.getEmployeePassword())) {
            // 비밀번호가 맞지 않습니다.
            throw new CustomException(ErrorCodeType.USER_PWD_INCORRECT);
        }
        // 새 비밀번호가 기존 비밀번호와 같으면 변경하지 않음
        else if (passwordEncoder.matches(modifyPwdDTO.getEmployeeNewPwd(), employee.getEmployeePassword())) {
            throw new CustomException(ErrorCodeType.PWD_SAME_AS_OLD);
        }
        // 전부 다 트루면 변경
        else {
            employee.modifyPwd(passwordEncoder.encode(modifyPwdDTO.getEmployeeNewPwd()));
            employeeRepository.save(employee);
        }

    }
}
