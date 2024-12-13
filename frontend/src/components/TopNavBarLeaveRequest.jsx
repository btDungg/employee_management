import { NavLink } from 'react-router-dom';
import '../../public/css/TopNavbarPoint.css'

const TopNavBarLeaveRequest = () => {
    return (
        <nav className="topnavbarpoint">
            <NavLink to="/leave-request/new" activeClassName="active">Gửi yêu cầu mới</NavLink>
            <NavLink to="/leave-request/history" activeClassName="active">Xem các yêu cầu trước</NavLink>
            <NavLink to="/leave-request/approve" activeClassName="active">Duyệt yêu cầu nghỉ phép</NavLink>
        </nav>
    );
};

export default TopNavBarLeaveRequest;