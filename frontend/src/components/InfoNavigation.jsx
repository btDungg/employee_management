import React from 'react';
import { useNavigate } from 'react-router-dom';
import "../../public/css/InfoNavigation.css";

const InfoNavigation = () => {
    const navigate = useNavigate();

    return (
        <div className="container mt-5">
            <div className="row justify-content-center">
                <div className="col-4 text-center">
                    <h2 className="mb-4 title">Xem</h2>
                    <button className="btn-form w-100 mb-2" onClick={() => navigate('/personal-info/summary')}>Xem thông tin cá nhân</button>
                    <button className="btn-form w-100 mb-2">Xem thông tin công việc</button>
                    <button className="btn-form w-100 mb-2">Xem thông tin điện thoại</button>
                    <button className="btn-form w-100 mb-2">Xem thông tin địa chỉ</button>
                    <button className="btn-form w-100 mb-2">Xem thông tin email</button>
                </div>
                <div className="col-4 text-center">
                    <h2 className="mb-4 title">Chỉnh sửa</h2>
                    <button className="btn-form w-100 mb-2">Sửa thông tin cá nhân</button>
                    <button className="btn-form w-100 mb-2">Sửa thông tin công việc</button>
                    <button className="btn-form w-100 mb-2">Sửa thông tin điện thoại</button>
                    <button className="btn-form w-100 mb-2">Sửa thông tin địa chỉ</button>
                    <button className="btn-form w-100 mb-2">Sửa thông tin email</button>
                </div>
            </div>
        </div>
    );
};

export default InfoNavigation;
