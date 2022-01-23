package nextstep.subway.applicaion;

import nextstep.subway.applicaion.dto.LineRequest;
import nextstep.subway.applicaion.dto.LineResponse;
import nextstep.subway.domain.Line;
import nextstep.subway.domain.LineRepository;
import nextstep.subway.ui.exception.UniqueKeyExistsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
public class LineService {

	private final LineRepository lineRepository;

	public LineService(LineRepository lineRepository) {
		this.lineRepository = lineRepository;
	}

	public LineResponse saveLine(LineRequest line) {
		validateCreateLine(line);
		return LineResponse.from(
				lineRepository.save(
						Line.of(line.getName(), line.getColor())));
	}

	private Line findLineById(long id) {
		return lineRepository.findById(id)
				.orElseThrow(EntityNotFoundException::new);
	}

	public LineResponse updateLine(long id, LineRequest request) {
		validateUpdateLine(id, request);

		final Line line = findLineById(id);

		line.update(request.toEntity());

		return LineResponse.from(line);
	}

	public void deleteLine(long id) {
		lineRepository.deleteById(id);
	}

	private void validateUpdateLine(long id, LineRequest line) {
		if (lineRepository.existsByNameAndIdIsNot(line.getName(), id)) {
			throw new UniqueKeyExistsException(line.getName());
		}
	}

	private void validateCreateLine(LineRequest line) {
		if (lineRepository.existsByName(line.getName())) {
			throw new UniqueKeyExistsException(line.getName());
		}
	}
}
