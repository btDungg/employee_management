import React from 'react';

const InfoNavigation = () => {
    return (
        <div className="container mt-5">
            <div className="row justify-content-center">
                {/* Chia thành 12 phần với khoảng trống mỗi bên là 2 phần */}
                <div className="col-4">
                    <h2>Xem</h2>
                    <button className="btn btn-primary w-100 mb-2">Xem thông tin cá nhân</button>
                    <button className="btn btn-primary w-100 mb-2">Xem thông tin công việc</button>
                    <button className="btn btn-primary w-100 mb-2">Xem thông tin điện thoại</button>
                    <button className="btn btn-primary w-100 mb-2">Xem thông tin địa chỉ</button>
                    <button className="btn btn-primary w-100 mb-2">Xem thông tin email</button>
                </div>
                <div className="col-4">
                    <h2>Chỉnh sửa</h2>
                    <button className="btn btn-secondary w-100 mb-2">Sửa thông tin cá nhân</button>
                    <button className="btn btn-secondary w-100 mb-2">Sửa thông tin công việc</button>
                    <button className="btn btn-secondary w-100 mb-2">Sửa thông tin điện thoại</button>
                    <button className="btn btn-secondary w-100 mb-2">Sửa thông tin địa chỉ</button>
                    <button className="btn btn-secondary w-100 mb-2">Sửa thông tin email</button>
                </div>
            </div>
        </div>
    );
};

export default InfoNavigation;
