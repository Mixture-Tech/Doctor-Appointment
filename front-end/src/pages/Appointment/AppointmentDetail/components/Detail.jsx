import React from 'react';
import Button from '../../../../components/Form/Button';

export default function AppointmentDetail() {
    const handleCancelClick = () => {
        alert('Lịch hẹn đã bị hủy.');
    };

    return (
        <>
            {/* Phần tiêu đề */}
            <div className="text-lg font-bold mb-4 bg-gray-200 p-4 rounded-md text-center">
                Chi tiết lịch hẹn
            </div>

            {/* Phần chi tiết lịch hẹn */}
            {/* <div className="mb-6 max-w-3xl mx-auto p-6 bg-white rounded-md shadow-lg"> */}
            {/* <h2 className="text-xl font-bold text-gray-700 mb-4 text-center">
                Thông tin lịch hẹn
            </h2> */}
            <div className="space-y-4 text-base text-gray-600">
                {/* Mỗi ý chính trong một khung */}
                <div className="flex items-start">
                    <span className="font-semibold w-32">Bác sĩ:</span>
                    <span className="text-gray-800">Nguyễn Khắc Đức</span>
                </div>
                <div className="flex items-start">
                    <span className="font-semibold w-32">Thời gian:</span>
                    <span className="text-gray-800">15:00 - 15:30 Ngày 15/11/2024</span>
                </div>
                <div className="flex items-start">
                    <span className="font-semibold w-32">Nơi khám:</span>
                    <span className="text-gray-800">Bệnh viện Ung bướu Hưng Việt</span>
                </div>
                <div className="flex items-start">
                    <span className="font-semibold w-32">Địa chỉ:</span>
                    <span className="text-gray-800">Đại Cồ Việt, Hai Bà Trưng, Hà Nội</span>
                </div>
                <div className="flex items-start">
                    <span className="font-semibold w-32">Ngày tạo:</span>
                    <span className="text-gray-800">15/11/2024</span>
                </div>
                <div className="flex items-start">
                    <span className="font-semibold w-32">Trạng thái:</span>
                    <span className="text-green-600 font-semibold">
                        Đặt lịch thành công
                    </span>
                </div>
            </div>
            {/* Đường dẫn tải app */}
            <p className="text-black text-center cursor-pointer mt-6">
                Qúy khách vui lòng tải App để nhận các thông báo và tham khảo các dịch vụ khác:
                <span className="text-blue-500 underline font-medium ml-1">Tại đây</span>
            </p>
            {/* </div> */}


            {/* Phần Hủy lịch */}
            <div className="bg-red-50 border border-red-300 text-red-600 rounded-lg p-6 mt-8 mx-auto max-w-xl mt-6 mb-12" >
                <div className="flex items-center justify-center mb-4">
                    <svg
                        xmlns="http://www.w3.org/2000/svg"
                        fill="none"
                        viewBox="0 0 24 24"
                        strokeWidth="2"
                        stroke="currentColor"
                        className="w-8 h-8 text-red-500"
                    >
                        <path
                            strokeLinecap="round"
                            strokeLinejoin="round"
                            d="M12 9v3m0 4h.01m-6.938 4h13.856C19.745 20 21 18.745 21 17.304V6.697C21 5.255 19.745 4 18.304 4H5.697C4.255 4 3 5.255 3 6.697v10.607C3 18.745 4.255 20 5.697 20z"
                        />
                    </svg>
                    <span className="ml-3 text-lg font-bold">Bạn có chắc chắn muốn hủy lịch hẹn?</span>
                </div>
                <p className="text-center text-sm text-gray-600 mb-6">
                    Nếu bạn hủy lịch, bạn sẽ không nhận được thông báo từ hệ thống và có thể ảnh hưởng đến lịch khám của bác sĩ.
                </p>
                <div className="flex justify-center">
                    <Button
                        onClick={handleCancelClick}
                        className="bg-red-500 hover:bg-red-600 text-white px-6 py-3 rounded-lg shadow-lg transition-all duration-200"
                    >
                        Hủy lịch hẹn
                    </Button>
                </div>
            </div>

        </>
    );
}
