package com.gil.couponsys02.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.gil.couponsys02.beans.Category;
import com.gil.couponsys02.beans.Company;
import com.gil.couponsys02.beans.Coupon;
import com.gil.couponsys02.exceptions.AlreadyInUseException;
import com.gil.couponsys02.exceptions.DataNotFoundException;
import com.gil.couponsys02.exceptions.ErrorsMessages;
import com.gil.couponsys02.exceptions.InvalidDataException;
import com.gil.couponsys02.exceptions.UnauthorizedAccessException;
import com.gil.couponsys02.login.ClientType;
import com.gil.couponsys02.login.LoginCacheManager;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@Scope("prototype")
public class CompanyServiceImpl extends ClientService implements CompanyService {

	private int comapnyId;

	@Override
	public ClientService login(String email, String password) throws UnauthorizedAccessException {
		Company company = this.companyRepository.findByEmailAndPassword(email, password);
		if (company == null) {
			throw new UnauthorizedAccessException(ErrorsMessages.LOGIN_FAILED, email, ClientType.COMPANY.name());
		}
		setComapnyId(company.getId());
		return this;
	}

	@Override
	public void addCoupon(Coupon coupon) throws AlreadyInUseException, InvalidDataException {
		if(coupon.getCompany().getId() != comapnyId) {
			throw new InvalidDataException(ErrorsMessages.WORNG_COMPANY_ID, coupon.getCompany().getId());
		}
		if (this.couponRepository.existsByCompanyIdAndTitle(coupon.getCompany().getId(), coupon.getTitle())) {
			throw new AlreadyInUseException(ErrorsMessages.COUPON_TITLE_ALREADY_IN_USE_BY_COMPANY,
					coupon.getCompany().getId(), coupon.getTitle());
		}
		this.couponRepository.save(coupon);
	}

	@Override
	public void updateCoupon(Coupon coupon) throws InvalidDataException, AlreadyInUseException {
		if(coupon.getCompany().getId() != comapnyId) {
			throw new InvalidDataException(ErrorsMessages.WORNG_COMPANY_ID, coupon.getCompany().getId());
		}
		if (!this.couponRepository.existsByIdAndCompanyId(coupon.getId(), coupon.getCompany().getId())) {
			throw new InvalidDataException(ErrorsMessages.COMPANY_REPLACED, coupon.getId());
		}
		if (this.couponRepository.existsByCompanyIdAndTitleAndIdIsNot(coupon.getCompany().getId(), coupon.getTitle(),
				coupon.getId())) {
			throw new AlreadyInUseException(ErrorsMessages.COUPON_TITLE_ALREADY_IN_USE_BY_COMPANY,
					coupon.getCompany().getId(), coupon.getTitle());
		}
		this.couponRepository.saveAndFlush(coupon);

	}

	@Override
	public void deleteCoupon(int couponId) throws DataNotFoundException {
		if (!this.couponRepository.existsByIdAndCompanyId(couponId, comapnyId)) {
			throw new DataNotFoundException(ErrorsMessages.COUPON_NOT_FOUND_BY_ID, comapnyId, couponId);
		}
		this.couponRepository.deleteCouponsFromCustomersCouponsByCouponId(couponId);
		this.couponRepository.deleteById(couponId);
	}

	@Override
	public List<Coupon> companyCoupons() {
		return this.couponRepository.findByCompanyId(comapnyId);
	}

	@Override
	public List<Coupon> getCompanyCoupons(Category category) {
		return this.couponRepository.findByCompanyIdAndCategory(comapnyId, category);
	}

	@Override
	public List<Coupon> getCompanyCoupons(double maxPrice) {
		return this.couponRepository.findByCompanyIdAndPriceLessThanEqual(comapnyId, maxPrice);
	}

	@Override
	public Company companyDetails() throws DataNotFoundException  {
		return this.companyRepository.findById(comapnyId).orElseThrow(() -> new DataNotFoundException(ErrorsMessages.NOT_FOUND_BY_ID,
				Company.class.getSimpleName(), comapnyId));

	}

	public int getComapnyId() {
		return comapnyId;
	}

	public void setComapnyId(int comapnyId) {
		this.comapnyId = comapnyId;
	}

}
