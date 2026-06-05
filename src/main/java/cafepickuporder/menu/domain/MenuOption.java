package cafepickuporder.menu.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "menu_options")
public class MenuOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id", nullable = false)
    private MenuOptionGroup optionGroup;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer additionalPrice;

    private Integer displayOrder;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public MenuOption(
            MenuOptionGroup optionGroup,
            String name,
            Integer additionalPrice,
            Integer displayOrder
    ) {
        this.optionGroup = optionGroup;
        this.name = name;
        this.additionalPrice = additionalPrice;
        this.displayOrder = displayOrder;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}