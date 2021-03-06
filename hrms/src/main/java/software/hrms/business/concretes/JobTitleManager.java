package software.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import software.hrms.business.abstracts.JobTitleService;
import software.hrms.business.constants.Messages;
import software.hrms.core.utilities.business.BusinessRules;
import software.hrms.core.utilities.results.DataResult;
import software.hrms.core.utilities.results.ErrorResult;
import software.hrms.core.utilities.results.Result;
import software.hrms.core.utilities.results.SuccessDataResult;
import software.hrms.core.utilities.results.SuccessResult;
import software.hrms.dataAccess.abstracts.JobTitleDao;
import software.hrms.entities.concretes.JobTitle;

@Service
public class JobTitleManager implements JobTitleService {

	private JobTitleDao jobTitleDao;

	@Autowired
	public JobTitleManager(JobTitleDao jobTitleDao) {
		super();
		this.jobTitleDao = jobTitleDao;
	}

	@Override
	public Result add(JobTitle jobTitle) {

		Result result = BusinessRules.run(existJobTitle(jobTitle.getTitle()));

		if (result != null) {
			return result;
		}

		this.jobTitleDao.save(jobTitle);
		return new SuccessResult(Messages.jobTitleAdded);
	}

	@Override
	public Result delete(JobTitle jobTitle) {
		this.jobTitleDao.delete(jobTitle);
		return new SuccessResult(Messages.jobTitleDeleted);
	}

	@Override
	public DataResult<List<JobTitle>> getAll() {
		return new SuccessDataResult<List<JobTitle>>(this.jobTitleDao.findAll(), Messages.jobTitlesListed);
	}

	private Result existJobTitle(String jobTitle) {
		if (this.jobTitleDao.getByTitleEquals(jobTitle) != null) {
			return new ErrorResult("Bu pozisyon ismi daha önce oluşturulmuştur");
		}
		return new SuccessResult();
	}

}
