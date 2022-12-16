package com.yanalexn.expense_limit_api.data_layer.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Account {//todo: could add serial version uid https://stackoverflow.com/questions/24573643/how-to-generate-serial-version-uid-in-intellij
//todo: could add validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;//todo: someday you can create uuid https://medium.com/@memredemir/creating-a-graphql-mysql-and-spring-boot-backend-service-32cec95f4436

    Long accountNumber;

//    @OneToMany(
//            cascade = CascadeType.ALL,
//            orphanRemoval = true)
//    @JoinColumn(name = "account_to_id")//тогда у orderr будет автоматически создаваться fk
//    private List<Transaction> orders;
}
