package homeless.monkey.com.mvc_task1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import homeless.monkey.com.mvc_task1.jsonViews.JsonViews;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @JsonView(JsonViews.UserSummary.class)
    private String name;

    @Column(nullable = false, unique = true)
    @JsonView(JsonViews.UserSummary.class)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonView(JsonViews.UserDetails.class)
    private List<OrderEntity> orders;
}
