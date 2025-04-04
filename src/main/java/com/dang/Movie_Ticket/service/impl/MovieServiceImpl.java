package com.dang.Movie_Ticket.service.impl;

import com.dang.Movie_Ticket.dto.request.MovieCreationRequest;
import com.dang.Movie_Ticket.dto.request.MovieUpdateRequest;
import com.dang.Movie_Ticket.dto.response.MovieDetailRespone;
import com.dang.Movie_Ticket.dto.response.PageResponse;
import com.dang.Movie_Ticket.entity.Movie;
import com.dang.Movie_Ticket.exception.AppException;
import com.dang.Movie_Ticket.exception.ErrorCode;
import com.dang.Movie_Ticket.mapper.MovieMapper;
import com.dang.Movie_Ticket.repository.MovieRepository;
import com.dang.Movie_Ticket.service.MovieService;
import com.dang.Movie_Ticket.service.UploadImageFile;
import com.dang.Movie_Ticket.util.enums.MovieStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService {
    private final UploadImageFile uploadImageFile;
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;


    @Override
    public String addMovie(MovieCreationRequest request, MultipartFile file) throws IOException {
        log.info("Thong tin tao: {}", request);
        Movie movie = Movie.builder()
                .title(request.getTitle())
                .genre(request.getGenre())
                .duration(request.getDuration())
                .rating(0)
                .synopsis(request.getSynopsis())
                .releaseDate(request.getReleaseDate())
                .poster(file != null ? uploadImageFile.uploadImage(file) : "khong co anh")
                .status(MovieStatus.COMING_SOON)
                .build();

        movieRepository.save(movie);
        return movie.getId();
    }

    @Override
    public MovieDetailRespone getMovie(String movieId) {
        return movieMapper.toMovieResponse(getMovieById(movieId));
    }

    @Override
    public PageResponse<?> getAllMovies(int pageNo, int pageSize, String sortBy) {
        int p = 0;
        if (pageNo > 0) {
            p = pageNo - 1;
        }

        List<Sort.Order> sorts = new ArrayList<>();
        // Nếu có điều kiện sắp xếp sortBy
        if (StringUtils.hasLength(sortBy)) {
            // điều kiện sort có 3 phần key_sort:asc|desc. với đk sort là bất kỳ field nào có trong entity
            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");

            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                if (matcher.group(3).equalsIgnoreCase("asc")) {
                    sorts.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
                } else {
                    sorts.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));

                }
            }
        }
        // Dùng Sort.by để sắp xếp theo thứ tự asc hay desc theo field truyền qua tham số sortBy
        Pageable pageable = PageRequest.of(p, pageSize, Sort.by(sorts));

        Page<Movie> movies = movieRepository.findAll(pageable);
        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPage(movies.getTotalPages())
                .items(movies.stream().map(movieMapper::toMovieResponse))
                .build();
    }

    @Override
    public void updateMovie(String movieId, MovieUpdateRequest request, MultipartFile file) throws IOException {
        Movie movie = getMovieById(movieId);
//        movie.setTitle(request.getTitle());
//        movie.setGenre(request.getGenre());
//        movie.setDuration(request.getDuration());
//        //movie.setRating();
//        movie.setSynopsis(request.getSynopsis());
//        movie.setReleaseDate(request.getReleaseDate());
        if (file != null) {
            movie.setPoster(uploadImageFile.uploadImage(file));
        }
        movieMapper.updateMovie(movie, request);

        movieRepository.save(movie);
    }

    @Override
    public void deleteMovie(String movieId) {
        movieRepository.deleteById(movieId);
        log.info("Movie deleted by id: {}", movieId);

    }

    @Override
    public void changeStatus(String movieId, MovieStatus status) {
        Movie movie = getMovieById(movieId);
        movie.setStatus(status);
        movieRepository.save(movie);

        log.info("Change movie status by id: {}, status: {}", movieId, status);
    }

    private Movie getMovieById(String movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new AppException(ErrorCode.MOVIE_NOT_EXISTED));
    }
}
