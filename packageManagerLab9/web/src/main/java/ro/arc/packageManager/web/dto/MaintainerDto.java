package ro.arc.packageManager.web.dto;

import lombok.*;
import ro.arc.packageManager.core.domain.Address;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class MaintainerDto extends BaseDto{
    private String userName;
    private String fullName;
    private String email;
    private Address address;
}
