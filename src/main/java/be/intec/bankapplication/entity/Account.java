package be.intec.bankapplication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
@Entity
public class Account{
    @Id
    @GeneratedValue(strategy   = GenerationType.IDENTITY)
    private long id;
    @Column(name = "Account_Holder_Name")
    private String accountHolderName;
    private double balance;
}
