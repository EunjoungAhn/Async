package dev.be.async.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AsyncService {

    private final EmailService emailService;

    public void asyncCall_1(){ //async 프로그래밍을 할때는 반드시 bean 주입을 받아야 한다.
        System.out.println("[asyncCall_1] :: " + Thread.currentThread().getName());
        //빈 주입을 받아서 사용하는 코드
        emailService.sendMail();
        emailService.sendMailWithCustomThreadPool();
        /*
        비동기로 동작할 수 있도록 Spring 이 한번 프록시로 Mapping 후, Sub Tread에게 위임한다.
        그러므로 Spring container에 등록되어 있는 빈을 사용해야 한다.
         */
    }

    public void asyncCall_2(){ //비동기 처리가 제대로 되지 않는다.
        System.out.println("[asyncCall_2] :: " + Thread.currentThread().getName());
        //인스턴스로 만들어서 동작하는지 테스트
        EmailService emailService = new EmailService(); //[asyncCall_2] :: http-nio-8080-exec-1
        emailService.sendMail(); //[sendMail] :: http-nio-8080-exec-1
        emailService.sendMailWithCustomThreadPool(); //[messagingTaskExecutor] :: http-nio-8080-exec-1
    }

    public void asyncCall_3(){ //비동기 처리가 제대로 되지 않는다.
        System.out.println("[asyncCall_3] :: " + Thread.currentThread().getName());
        sendMail();
    }

    //내부 메서드를 Async로 만들어서 동작하는지 테스트
    @Async
    public void sendMail(){
        System.out.println("[sendMail] :: " + Thread.currentThread().getName());
    }
}
