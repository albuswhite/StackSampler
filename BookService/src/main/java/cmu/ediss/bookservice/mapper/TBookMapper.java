package cmu.ediss.bookservice.mapper;



import cmu.ediss.bookservice.entity.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TBookMapper extends BaseMapper<Book> {
}
