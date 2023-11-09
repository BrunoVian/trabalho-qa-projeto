package br.com.SecurityProfit.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailGmailDTO {
    private String loginEmail;
    private String senhaEmail;
}
