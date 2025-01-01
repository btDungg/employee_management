package uni.hcmus.employeemanagement.service.serviceImplement;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.hcmus.employeemanagement.dto.Response.ActivityDTO;
import uni.hcmus.employeemanagement.dto.Response.DetailActivityDTO;
import uni.hcmus.employeemanagement.dto.Response.EmployeePublicDto_v1;
import uni.hcmus.employeemanagement.entity.Activity;
import uni.hcmus.employeemanagement.entity.ActivityDetail;
import uni.hcmus.employeemanagement.entity.Employee;
import uni.hcmus.employeemanagement.entity.MetaData;
import uni.hcmus.employeemanagement.exception_handler.exceptions.AccessDeniedException;
import uni.hcmus.employeemanagement.repository.ActivityRepository;
import uni.hcmus.employeemanagement.repository.DetailActivityRepository;
import uni.hcmus.employeemanagement.repository.EmployeeRepository;
import uni.hcmus.employeemanagement.service.interfaceService.IActivityService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActivityService implements IActivityService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DetailActivityRepository detailActivityRepository;
    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public Optional<List<ActivityDTO>> getActivities(String name, String email) {
        // Kiểm tra quyền truy cập của nhân viên
        Optional<Employee> emp = employeeRepository.findByEmailCompany(email);

        // Nếu người dùng là HR, lấy tất cả hoạt động
        if ("HR".equals(emp.get().getType())) {
            List<Activity> activities;
            if (name == null || name.isEmpty()) {
                activities = activityRepository.findAll(); // Lấy tất cả hoạt động nếu không có tên
            } else {
                activities = activityRepository.findByNameContaining(name); // Lọc hoạt động theo tên
            }

            // Chuyển đổi danh sách Activity thành danh sách ActivityDTO
            List<ActivityDTO> activityDTOs = activities.stream()
                    .map(activity -> {
                        // Lấy thông tin nhân viên tạo
                        String creatorName = "";
                        if (activity.getCreatedBy() != null) {
                            creatorName = activity.getCreatedBy().getUsername(); // Lấy tên nhân viên tạo
                        }

                        // Tạo và trả về ActivityDTO
                        return new ActivityDTO(
                                activity.getId(),
                                activity.getName(),
                                activity.getActivityType(),
                                activity.getStartDate(),
                                activity.getEndDate(),
                                activity.getNumberOfParticipants(),
                                activity.getNumberOfRegistered(),
                                activity.getCreatedDate(),
                                activity.getRegistrationOpenDate(),
                                activity.getRegistrationCloseDate(),
                                activity.getStatus(),
                                activity.getDescription(),
                                activity.getIsViewed(),
                                activity.getCreatedBy() != null ? activity.getCreatedBy().getId() : null,  // Lấy ID nhân viên tạo
                                creatorName  // Tên nhân viên tạo
                        );
                    })
                    .collect(Collectors.toList());

            return Optional.of(activityDTOs);
        }

        // Nếu không phải HR, chỉ trả về các hoạt động có isViewed = 1
        List<Activity> activities = activityRepository.findByIsViewed(Boolean.TRUE);

        // Lọc hoạt động theo tên nếu có
        if (name != null && !name.isEmpty()) {
            activities = activities.stream()
                    .filter(activity -> activity.getName().contains(name))
                    .collect(Collectors.toList());
        }

        // Chuyển đổi danh sách Activity thành danh sách ActivityDTO
        List<ActivityDTO> activityDTOs = activities.stream()
                .map(activity -> {
                    // Lấy thông tin nhân viên tạo
                    String creatorName = "";
                    if (activity.getCreatedBy() != null) {
                        creatorName = activity.getCreatedBy().getUsername(); // Lấy tên nhân viên tạo
                    }

                    // Tạo và trả về ActivityDTO
                    return new ActivityDTO(
                            activity.getId(),
                            activity.getName(),
                            activity.getActivityType(),
                            activity.getStartDate(),
                            activity.getEndDate(),
                            activity.getNumberOfParticipants(),
                            activity.getNumberOfRegistered(),
                            activity.getCreatedDate(),
                            activity.getRegistrationOpenDate(),
                            activity.getRegistrationCloseDate(),
                            activity.getStatus(),
                            activity.getDescription(),
                            activity.getIsViewed(),
                            activity.getCreatedBy() != null ? activity.getCreatedBy().getId() : null,  // Lấy ID nhân viên tạo
                            creatorName  // Tên nhân viên tạo
                    );
                })
                .collect(Collectors.toList());

        return Optional.of(activityDTOs);
    }

    @Override
    public Optional<DetailActivityDTO> getDetailActivity(Long id, String email) {

        // Kiểm tra xem nhân viên có tồn tại không
        Employee emp = employeeRepository.findByEmailCompany(email).orElse(null);
        if (emp == null) {
            return Optional.empty();  // Nếu không có nhân viên, trả về Optional.empty()
        }

        // Tìm kiếm chi tiết hoạt động (đã đăng ký hoặc chưa đăng ký)
        Optional<ActivityDetail> detailActivity = detailActivityRepository.findByEmployeeIdAndActivityId(emp.getId(), id);


        // Nếu tìm thấy chi tiết hoạt động
        if (detailActivity.isPresent()) {
            ActivityDetail activity = detailActivity.get();

            // Chuyển đổi thông tin hoạt động và nhân viên thành DTO
            ActivityDTO activityDTO = new ActivityDTO(
                    activity.getActivity().getId(),
                    activity.getActivity().getName(),
                    activity.getActivity().getActivityType(),
                    activity.getActivity().getStartDate(),
                    activity.getActivity().getEndDate(),
                    activity.getActivity().getNumberOfParticipants(),
                    activity.getActivity().getNumberOfRegistered(),
                    activity.getActivity().getCreatedDate(),
                    activity.getActivity().getRegistrationOpenDate(),
                    activity.getActivity().getRegistrationCloseDate(),
                    activity.getActivity().getStatus(),
                    activity.getActivity().getDescription(),
                    activity.getActivity().getIsViewed(),
                    activity.getActivity().getCreatedBy() != null ? activity.getActivity().getCreatedBy().getId() : null,
                    activity.getActivity().getCreatedBy() != null ? activity.getActivity().getCreatedBy().getUsername() : null
            );

            EmployeePublicDto_v1 myself = new EmployeePublicDto_v1(
                    activity.getEmployee().getId(),
                    activity.getEmployee().getName(),
                    activity.getEmployee().getType(),
                    activity.getEmployee().getEmailCompany(),
                    activity.getEmployee().getOrganization().getId()
            );

            // Chuyển đổi DetailActivity thành DetailActivityDTO
            DetailActivityDTO dto = new DetailActivityDTO(
                    activity.getId(),
                    activityDTO,
                    myself,
                    activity.getResult(),
                    activity.getEvidence(),
                    activity.getRanking(),
                    activity.getStatus(),
                    activity.getRegisteredDate()
            );

            return Optional.of(dto);
        } else {
            // Nếu không tìm thấy chi tiết đăng ký, chỉ trả về thông tin về hoạt động

        Optional<Activity> activity = activityRepository.findById(id);

        if (activity.isPresent()&&activity.get().getIsViewed()) {
                ActivityDTO activityDTO = new ActivityDTO(
                        activity.get().getId(),
                        activity.get().getName(),
                        activity.get().getActivityType(),
                        activity.get().getStartDate(),
                        activity.get().getEndDate(),
                        activity.get().getNumberOfParticipants(),
                        activity.get().getNumberOfRegistered(),
                        activity.get().getCreatedDate(),
                        activity.get().getRegistrationOpenDate(),
                        activity.get().getRegistrationCloseDate(),
                        activity.get().getStatus(),
                        activity.get().getDescription(),
                        activity.get().getIsViewed(),
                        activity.get().getCreatedBy() != null ? activity.get().getCreatedBy().getId() : null,
                        activity.get().getCreatedBy() != null ? activity.get().getCreatedBy().getUsername() : null
                );

                EmployeePublicDto_v1 myself = new EmployeePublicDto_v1(
                        emp.getId(),
                        emp.getName(),
                        emp.getType(),
                        emp.getEmailCompany(),
                        emp.getOrganization().getId()
                );
                // Trả về thông tin hoạt động mà nhân viên chưa đăng ký
                return Optional.of(new DetailActivityDTO(
                        null,
                        activityDTO,
                        myself,
                        null,
                        null,
                        0,
                        "Not Registered",
                        null
                ));
            } else {
            return Optional.empty();  // Nếu không tìm thấy hoạt động, trả về Optional.empty()
        }
        }
    }


    @Override
    public Optional<DetailActivityDTO> registerActivity(Long id, String email) {
        // Tìm nhân viên theo email
        Employee emp = employeeRepository.findByEmailCompany(email).orElse(null);
        if (emp == null) {
            return Optional.empty(); // Nhân viên không tồn tại
        }

        // Tìm hoạt động theo id
        Activity activity = activityRepository.findById(id).orElse(null);
        if (activity == null) {
            return Optional.empty(); // Hoạt động không tồn tại
        }


        //Hr có thể đăng ký hoạt động bất cứ lúc nào
        if(!emp.getType().equals("HR")){


            // Kiểm tra số lượng đăng ký
            if (activity.getNumberOfRegistered() >= activity.getNumberOfParticipants()) {
                throw new AccessDeniedException("Hoạt động đã đầy, không thể đăng ký thêm."); // Ném ngoại lệ nếu đầy
            }
            if(activity.getRegistrationOpenDate().isAfter(LocalDate.now())){
                throw new AccessDeniedException("Hoạt động chưa mở đăng ký, không thể đăng ký hoạt động này."); // Nếu chưa mở đăng ký, ném ngoại lệ
            }
            if(activity.getRegistrationCloseDate().isBefore(LocalDate.now())){
                throw new AccessDeniedException("Hoạt động đã đóng đăng ký, Không thể đăng ký hoạt động này."); // Nếu đã đóng đăng ký, ném ngoại lệ
            }



        }
        // Kiểm tra xem đã đăng ký chưa
        Optional<ActivityDetail> existingRegistration = detailActivityRepository.findByEmployeeIdAndActivityId(emp.getId(), id);
        if (existingRegistration.isPresent()) {
            throw new IllegalArgumentException("Nhân viên đã đăng ký hoạt động này."); // Nếu đã đăng ký, ném ngoại lệ
        }
        // Tạo mới bản ghi đăng ký
        ActivityDetail detailActivity = new ActivityDetail();
        detailActivity.setActivity(activity);
        detailActivity.setEmployee(emp);
        detailActivity.setStatus("Registered");
        detailActivity.setRegisteredDate(LocalDate.now());

        // Lưu bản ghi vào cơ sở dữ liệu
        ActivityDetail savedDetail = detailActivityRepository.save(detailActivity);

        // Cập nhật số lượng đã đăng ký cho hoạt động
        activity.setNumberOfRegistered(activity.getNumberOfRegistered() + 1);
        activityRepository.save(activity);

        // Chuyển đổi sang DTO
        DetailActivityDTO dto = new DetailActivityDTO(
                savedDetail.getId(),
                new ActivityDTO(
                        activity.getId(),
                        activity.getName(),
                        activity.getActivityType(),
                        activity.getStartDate(),
                        activity.getEndDate(),
                        activity.getNumberOfParticipants(),
                        activity.getNumberOfRegistered(),
                        activity.getCreatedDate(),
                        activity.getRegistrationOpenDate(),
                        activity.getRegistrationCloseDate(),
                        activity.getStatus(),
                        activity.getDescription(),
                        activity.getIsViewed(),
                        activity.getCreatedBy() != null ? activity.getCreatedBy().getId() : null,
                        activity.getCreatedBy() != null ? activity.getCreatedBy().getUsername() : null
                ),
                new EmployeePublicDto_v1(
                        emp.getId(),
                        emp.getName(),
                        emp.getType(),
                        emp.getEmailCompany(),
                        emp.getOrganization().getId()
                ),
                savedDetail.getResult(),
                savedDetail.getEvidence(),
                savedDetail.getRanking(),
                savedDetail.getStatus(),
                savedDetail.getRegisteredDate()
        );

        return Optional.of(dto); // Trả về DTO
    }


    @Override
    public Optional<DetailActivityDTO> unregisterActivity(Long id, String email) {
        // Tìm nhân viên theo email
        Employee emp = employeeRepository.findByEmailCompany(email).orElse(null);
        if (emp == null) {
            return Optional.empty(); // Nhân viên không tồn tại
        }

        // Tìm hoạt động theo id
        Activity activity = activityRepository.findById(id).orElse(null);
        if (activity == null) {
            return Optional.empty(); // Hoạt động không tồn tại
        }


        // Tìm bản ghi đăng ký của nhân viên cho hoạt động này
        Optional<ActivityDetail> existingRegistration = detailActivityRepository.findByEmployeeIdAndActivityId(emp.getId(), id);
        if (existingRegistration.isEmpty()) {
            throw new IllegalArgumentException("Nhân viên chưa đăng ký hoạt động này."); // Nếu chưa đăng ký, ném ngoại lệ
        }

        // Xóa bản ghi đăng ký
        ActivityDetail detailActivity = existingRegistration.get();
        if (!emp.getType().equals("HR")) {
            if (detailActivity.getActivity().getRegistrationOpenDate().isAfter(LocalDate.now())) {
                throw new AccessDeniedException("Hoạt động này chưa mở đăng ký, không thể hủy.");
            }
            if (detailActivity.getActivity().getRegistrationCloseDate().isBefore(LocalDate.now())) {
                throw new AccessDeniedException("Hoạt động này đã đóng đăng ký, không thể hủy.");
            }
        }

        detailActivityRepository.delete(detailActivity);

        // Cập nhật số lượng đã đăng ký của hoạt động
        activity.setNumberOfRegistered(activity.getNumberOfRegistered() - 1);
        activityRepository.save(activity);

        // Chuyển đổi ActivityDetail thành DTO (nếu cần trả về thông tin)
        DetailActivityDTO dto = new DetailActivityDTO(
                detailActivity.getId(),
                new ActivityDTO(
                        activity.getId(),
                        activity.getName(),
                        activity.getActivityType(),
                        activity.getStartDate(),
                        activity.getEndDate(),
                        activity.getNumberOfParticipants(),
                        activity.getNumberOfRegistered(),
                        activity.getCreatedDate(),
                        activity.getRegistrationOpenDate(),
                        activity.getRegistrationCloseDate(),
                        activity.getStatus(),
                        activity.getDescription(),
                        activity.getIsViewed(),
                        activity.getCreatedBy() != null ? activity.getCreatedBy().getId() : null,
                        activity.getCreatedBy() != null ? activity.getCreatedBy().getUsername() : null
                ),
                new EmployeePublicDto_v1(
                        emp.getId(),
                        emp.getName(),
                        emp.getType(),
                        emp.getEmailCompany(),
                        emp.getOrganization().getId()
                ),
                detailActivity.getResult(),
                detailActivity.getEvidence(),
                detailActivity.getRanking(),
                detailActivity.getStatus(),
                detailActivity.getRegisteredDate()
        );

        return Optional.of(dto);
    }


}
