import { NavLink } from 'react-router-dom';
import '../../public/css/TopNavbarPoint.css';

const TopNavbarPoint = () => {
    return (
        <nav className="topnavbarpoint">
            <NavLink to="/point-info" activeClassName="active">Thông tin điểm</NavLink>
            <NavLink to="/view-other-points" activeClassName="active">Xem điểm NV khác</NavLink>
            <NavLink to="/point-history" activeClassName="active">Lịch sử điểm</NavLink>
            <NavLink to="/change-points" activeClassName="active">Thay đổi điểm</NavLink>
        </nav>
    );
};

export default TopNavbarPoint;