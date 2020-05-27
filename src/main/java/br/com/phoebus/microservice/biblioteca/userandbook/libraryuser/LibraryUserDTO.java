package br.com.phoebus.microservice.biblioteca.userandbook.libraryuser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderClassName = "Builder")
public class LibraryUserDTO {

    private Long id;

    @NotEmpty(message = "Name may is not be empty")
    @Size(min = 2)
    private String name;

    @Min(3)
    private int age;

    @NotEmpty(message = "Telephone may not be empty")
    @Size(min = 8)
    private String telephone;

    private String specificIDLoan;

    public static LibraryUserDTO from(LibraryUser libraryUser) {
        return LibraryUserDTO.builder()
                .id(libraryUser.getId())
                .age(libraryUser.getAge())
                .name(libraryUser.getName())
                .telephone(libraryUser.getTelephone())
                .specificIDLoan(libraryUser.getSpecificIDLoan())
                .build();
    }

    public static List<LibraryUserDTO> from(List<LibraryUser> libraryUserList) {
        List<LibraryUserDTO> libraryUserDTOList = new ArrayList<>();
        for (LibraryUser libraryUser : libraryUserList) {
            libraryUserDTOList.add(LibraryUserDTO.from(libraryUser));
        }
        return libraryUserDTOList;
    }

    public static Page<LibraryUserDTO> from(Page<LibraryUser> pages) {
        return pages.map(LibraryUserDTO::from);
    }
}
