package br.com.phoebus.microservice.biblioteca.userandbook.libraryuser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "library_user")
@Builder(builderClassName = "Builder")
public class LibraryUser implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "library_user_id")
    private Long id;

    private String name;

    private int age;

    private String telephone;

    private String specificId;

    public static LibraryUser to(LibraryUserDTO libraryUserDTO) {
        return LibraryUser.builder()
                .id(libraryUserDTO.getId())
                .telephone(libraryUserDTO.getTelephone())
                .name(libraryUserDTO.getName())
                .age(libraryUserDTO.getAge())
                .specificId(libraryUserDTO.getSpecificId())
                .build();
    }

    public static List<LibraryUser> to(List<LibraryUserDTO> libraryUserDTOList) {
        List<LibraryUser> libraryUserList = new ArrayList<>();
        for (LibraryUserDTO libraryUserDTO : libraryUserDTOList) {
            libraryUserList.add(LibraryUser.to(libraryUserDTO));
        }
        return libraryUserList;
    }

    public static Page<LibraryUser> to(Page<LibraryUserDTO> pages) {
        return pages.map(LibraryUser::to);
    }

}
