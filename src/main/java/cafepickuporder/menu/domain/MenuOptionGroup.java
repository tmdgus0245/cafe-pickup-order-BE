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
@Table(name = "menu_option_groups")
public class MenuOptionGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean required;

    private Integer minSelect;
    private Integer maxSelect;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public MenuOptionGroup(
            Menu menu,
            String name,
            Boolean required,
            Integer minSelect,
            Integer maxSelect
    ) {
        this.menu = menu;
        this.name = name;
        this.required = required;
        this.minSelect = minSelect;
        this.maxSelect = maxSelect;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}