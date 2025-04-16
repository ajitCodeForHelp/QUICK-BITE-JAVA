package com.quickBite.primary.service;

import com.quickBite.bean.KeyValueDto;
import com.quickBite.configuration.MongoConfig;
import com.quickBite.configuration.SpringBeanContext;
import com.quickBite.exception.BadRequestException;
import com.quickBite.primary.dto.VendorDto;
import com.quickBite.primary.mapper.VendorMapper;
import com.quickBite.primary.pojo.UserAdmin;
import com.quickBite.primary.pojo.Vendor;
import com.quickBite.primary.pojo.enums.RoleEnum;
import com.quickBite.primary.repository.PrimarySequenceRepository;
import com.quickBite.security.JwtUserDetailsService;
import com.quickBite.utils.TextUtils;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.quickBite.configuration.SpringBeanContext.getBean;

@Slf4j
@Service
public class VendorService extends _BaseService {

    public String save(VendorDto.CreateVendor vendorDto) throws BadRequestException {
        UserAdmin loggedInUser = (UserAdmin) getBean(JwtUserDetailsService.class).getLoggedInUser();
        Vendor vendor = vendorRepository.findByUsername(vendorDto.getUsername());
        if (vendor != null) {
            throw new BadRequestException("Username is already assigned to other user.");
        }
        vendor = VendorMapper.MAPPER.mapToPojo(vendorDto);
        vendor.setAdminId(loggedInUser.getObjectId());
        vendor.setSeqId(getBean(PrimarySequenceRepository.class).getNextSequenceId(Vendor.class.getSimpleName()));
        vendor.setDatabaseDetail(getDatabaseDetail(vendor));
        vendor.setPwdText(vendorDto.getPassword());
        vendor.setPwdSecure(TextUtils.getEncodedPassword(vendorDto.getPassword()));
        vendor.setUserType(RoleEnum.ROLE_VENDOR);
        vendor = vendorRepository.save(vendor);
        return vendor.getId();
    }

    private Vendor.Database getDatabaseDetail(Vendor vendor) {
        Vendor.Database databaseDetail = new Vendor.Database(
                SpringBeanContext.getBean(MongoConfig.class).DataBaseURI,
                SpringBeanContext.getBean(MongoConfig.class).getClientDataBase(
                        SpringBeanContext.getBean(MongoConfig.class).DataBase,
                        vendor.getSeqId(), TextUtils.validateTitle(vendor.getFirstName()))
        );
        return databaseDetail;
    }

    public void update(String id, VendorDto.UpdateVendor vendorDto) throws BadRequestException {
        Vendor vendor = findById(id);
        vendor = VendorMapper.MAPPER.mapToPojo(vendor, vendorDto);
        vendorRepository.save(vendor);
    }

    public VendorDto.DetailVendor get(String id) throws BadRequestException {
        Vendor vendor = findById(id);
        return VendorMapper.MAPPER.mapToDetailDto(vendor);
    }

    public List<VendorDto.DetailVendor> list(String data) {
        // Data >  Active | Inactive  | All
        List<Vendor> list = null;
        if (data.equals("Active")) {
            list = vendorRepository.findByActive(true);
        } else if (data.equals("Inactive")) {
            list = vendorRepository.findByActive(false);
        } else {
            list = vendorRepository.findAll();
        }
        return list.stream()
                .map(vendor -> VendorMapper.MAPPER.mapToDetailDto(vendor))
                .collect(Collectors.toList());
    }

    public void activate(String id) throws BadRequestException {
        Vendor vendor = findById(id);
        vendor.setActive(true);
        vendor.setModifiedAt(LocalDateTime.now());
        vendorRepository.save(vendor);
    }

    public void inactivate(String id) throws BadRequestException {
        Vendor vendor = findById(id);
        vendor.setActive(false);
        vendor.setModifiedAt(LocalDateTime.now());
        vendorRepository.save(vendor);
    }

    public void createVendor(VendorDto.CreateVendorRequest request) throws BadRequestException {
        // Only Used For General Form Creation > Where Vendor Can Register Itself >

        Vendor vendor = vendorRepository.findByUsername(request.getMobileNumber());
        if (vendor != null) {
            throw new BadRequestException("Please provide a unique mobile number");
        }
        vendor = new Vendor();
        vendor.setFirstName(request.getFirstName());
        vendor.setLastName(request.getLastName());
        vendor.setUsername(request.getMobileNumber());
        vendor.setPwdText(request.getMobileNumber());
        vendor.setPwdSecure(TextUtils.getEncodedPassword(request.getMobileNumber()));
        vendor.setUserType(RoleEnum.ROLE_VENDOR);
        vendor.setSeqId(getBean(PrimarySequenceRepository.class).getNextSequenceId(Vendor.class.getSimpleName()));
        vendor.setDatabaseDetail(getDatabaseDetail(vendor));
        vendorRepository.save(vendor);
    }

    public Vendor findById(String id) throws BadRequestException {
        Vendor vendor = vendorRepository.findById(new ObjectId(id)).orElse(null);
        if (vendor == null) {
            throw new BadRequestException("Vendor Record Not Exist");
        }
        return vendor;
    }

    public void resetPassword(String id, VendorDto.ResetPassword request) throws BadRequestException {
        Vendor vendor = findById(id);
        vendor.setPwdText(request.getPassword());
        vendor.setPwdSecure(TextUtils.getEncodedPassword(request.getPassword()));
        vendorRepository.save(vendor);
    }

    public Vendor findByUsername(String userName) throws BadRequestException {
        Vendor vendor = vendorRepository.findByUsername(userName);
        if (vendor == null) {
            throw new BadRequestException("Vendor Record Not Exist");
        }
        return vendor;
    }

    public List<KeyValueDto> vendorKeyList() {
        UserAdmin loggedInUser = (UserAdmin) getBean(JwtUserDetailsService.class).getLoggedInUser();
        List<Vendor> vendorList = vendorRepository.findByAdminId(loggedInUser.getObjectId());
        if(TextUtils.isEmpty(vendorList)) return null;
        return vendorList.stream()
                .map(vendor -> VendorMapper.MAPPER.mapToKeyValueDto(vendor))
                .toList();
    }
}