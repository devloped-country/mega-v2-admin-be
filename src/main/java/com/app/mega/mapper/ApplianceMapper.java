import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ApplianceMapper {

    @Select("SELECT status FROM appliance WHERE id = #{id}")
    Long getStatusById(@Param("id") Long id);
}