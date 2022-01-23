package nextstep.subway.applicaion;


import nextstep.subway.applicaion.dto.LineResponse;
import nextstep.subway.domain.Line;
import nextstep.subway.domain.LineRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class LineQueryService {

	private final LineRepository lineRepository;

	public LineQueryService(LineRepository lineRepository) {
		this.lineRepository = lineRepository;
	}

	public List<LineResponse> getAllLines() {
		return LineResponse.fromList(lineRepository.findAll());
	}

	public LineResponse getLine(long id) {
		final Line line = findLineById(id);
		return LineResponse.from(line);
	}

	private Line findLineById(long id) {
		return lineRepository.findById(id)
				.orElseThrow(EntityNotFoundException::new);
	}
}

