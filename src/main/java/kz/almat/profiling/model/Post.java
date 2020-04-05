package kz.almat.profiling.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Almat on 27.03.2020
 */

@Entity
@Table(name = "P_POST")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(generator = "P_POST_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequenceName = "P_POST_ID_SEQ", name = "P_POST_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "P_TOPIC", nullable = false)
    private String topic;

    @Column(name = "P_MESSAGE", nullable = false)
    private String message;

}
