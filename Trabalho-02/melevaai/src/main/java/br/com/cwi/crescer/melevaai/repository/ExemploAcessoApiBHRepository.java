package br.com.cwi.crescer.melevaai.repository;

import br.com.cwi.crescer.melevaai.security.bh.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "https://crescer-api-auth.herokuapp.com" )
public interface ExemploAcessoApiBHRepository {

    @RequestMapping(method = RequestMethod.GET, value = "/me")
    UserResponse pegarUsuarioAPIBH(@RequestHeader("Authorization") String token);

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    UserResponse logarAPIBH(@RequestHeader("Authorization") String token);

}
