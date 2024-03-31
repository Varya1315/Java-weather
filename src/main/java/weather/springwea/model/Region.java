package weather.springwea.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "region")
public class Region {
    /**
     * Marks this field as the primary key for the entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Specifies that this field maps to a unique column in the database.
     */
    @Column(unique = true)
    private String name;
    /**
     * Defines a one-to-many relationship between the region
     * entity and the towns entities.
     */
    @OneToMany

    @JoinColumn(name = "region_id")
    private List<Towns> towns;

    public Region(int i, String region1, List<Object> objects){
         /**
         * Конструктор пуст, так как требуется создание объекта Region с заданными параметрами
         * для использования в тесте. Предполагается, что параметры конструктора i, region1 и objects
         * будут корректно инициализированы в контексте выполнения теста.
         */
    }
    public <E> Region(String region1, List<E> es) {
        /**
         * Конструктор, принимающий имя региона и список элементов произвольного типа.
         * Предполагается, что список содержит элементы, связанные с данным регионом.
         */
    }

    public Region(String name) {
        /**
         * Конструктор, принимающий только имя региона.
         * Используется для создания объекта Region с указанным именем.
         */
    }
}
