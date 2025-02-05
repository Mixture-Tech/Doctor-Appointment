import React, { useState, useEffect } from "react";
import { useNavigate, useOutletContext } from "react-router-dom";
import { fetchAppointments } from "../../../../services/apis/admin/appointment-management";
import { formatDate, formatTimeRange } from "../../../../utils/date-time-formatter";
import { searchAppointments } from "../../../../services/apis/admin/appointment-management";

export default function Content() {
    const navigate = useNavigate();
    const { searchParams } = useOutletContext();
    const [appointments, setAppointments] = useState([]);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const loadAppointments = async () => {
            try {
                setLoading(true);
                let data;
                
                if (Object.keys(searchParams).length > 0) {
                    data = await searchAppointments(searchParams, currentPage, 10);
                } else {
                    data = await fetchAppointments(currentPage, 10);
                }
                
                setAppointments(data.content);
                setTotalPages(data.totalPages);
                setError(null);
            } catch (err) {
                setError(err.message || "Có lỗi xảy ra khi tải dữ liệu");
            } finally {
                setLoading(false);
            }
        };

        loadAppointments();
    }, [currentPage, searchParams]);

    const handlePaginationClick = (newPage) => {
        if (newPage >= 0 && newPage < totalPages) {
            setCurrentPage(newPage);
        }
    };

    const getStatusInVietnamese = (status) => {
        switch (status) {
            case "PENDING": return "Chờ khám";
            case "COMPLETED": return "Đã khám";
            case "NO_SHOW": return "Không có mặt";
            default: return status;
        }
    };

    // const formatDateTime = (dateStr) => {
    //     const date = new Date(dateStr);
    //     return date.toLocaleDateString('vi-VN');
    // };

    const navigateToEdit = (appointment) => {
        navigate(`/admin/chinh-sua-lich-hen/${appointment.appointment_id}`, 
            { state: { appointment } 
        });
    };

    if (loading) {
        return <div className="p-6">Đang tải dữ liệu...</div>;
    }

    if (error) {
        return <div className="p-6 text-red-500">Lỗi: {error}</div>;
    }

    return (
        <div className="p-6 space-y-6">
            <div className="bg-white p-6 rounded-lg shadow">
                <h2 className="text-lg font-semibold mb-4">Danh sách lịch hẹn</h2>
                <table className="table-auto w-full border-collapse border border-gray-300">
                    <thead>
                        <tr className="bg-gray-200">
                            <th className="border border-gray-300 px-4 py-2 font-semibold">Mã lịch hẹn</th>
                            <th className="border border-gray-300 px-4 py-2 font-semibold">Ngày khám</th>
                            <th className="border border-gray-300 px-4 py-2 font-semibold">Giờ khám</th>
                            <th className="border border-gray-300 px-4 py-2 font-semibold">Bệnh nhân</th>
                            <th className="border border-gray-300 px-4 py-2 font-semibold">Số điện thoại</th>
                            <th className="border border-gray-300 px-4 py-2 font-semibold">Bác sĩ</th>
                            <th className="border border-gray-300 px-4 py-2 font-semibold">Trạng thái</th>
                            <th className="border border-gray-300 px-4 py-2 font-semibold">Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        {appointments.map((appointment) => (
                            <tr key={appointment.appointment_id}>
                                <td className="border border-gray-300 px-4 py-2 text-center">
                                    {appointment.appointment_code}
                                </td>
                                <td className="border border-gray-300 px-4 py-2">
                                    {formatDate(appointment.taken_date)}
                                </td>
                                <td className="border border-gray-300 px-4 py-2">
                                    {formatTimeRange(appointment.start_time, appointment.end_time)}
                                </td>
                                <td className="border border-gray-300 px-4 py-2">
                                    {appointment.username}
                                </td>
                                <td className="border border-gray-300 px-4 py-2">
                                    {appointment.phone}
                                </td>
                                <td className="border border-gray-300 px-4 py-2">
                                    {appointment.doctor_name}
                                </td>
                                <td className="border border-gray-300 px-4 py-2 text-center">
                                    {getStatusInVietnamese(appointment.clinic_status)}
                                </td>
                                <td className="border border-gray-300 px-4 py-2 text-center">
                                    <button 
                                        className="bg-primary-500 text-white px-3 py-0.5 rounded hover:bg-primary-800"
                                        onClick={() => navigateToEdit(appointment)}
                                    >
                                        Thay đổi
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>

                {/* Phân trang */}
                <div className="flex justify-center space-x-2 mt-4">
                    <button 
                        className={`px-2 py-1 border border-gray-300 rounded ${
                            currentPage === 0 ? "bg-gray-300 text-gray-500" : "hover:bg-[#48CFCB] hover:text-white"
                        }`}
                        onClick={() => handlePaginationClick(currentPage - 1)}
                        disabled={currentPage === 0}
                    >
                        &lt;&lt;
                    </button>

                    {[...Array(totalPages)].map((_, index) => (
                        <button
                            key={index}
                            className={`px-3 py-1 border border-gray-300 rounded ${
                                currentPage === index ? "bg-[#48CFCB] text-white" : "hover:bg-[#48CFCB] hover:text-white"
                            }`}
                            onClick={() => handlePaginationClick(index)}
                        >
                            {index + 1}
                        </button>
                    ))}

                    <button
                        className={`px-2 py-1 border border-gray-300 rounded ${
                            currentPage === totalPages - 1 ? "bg-gray-300 text-gray-500" : "hover:bg-[#48CFCB] hover:text-white"
                        }`}
                        onClick={() => handlePaginationClick(currentPage + 1)}
                        disabled={currentPage === totalPages - 1}
                    >
                        &gt;&gt;
                    </button>
                </div>
            </div>
        </div>
    );
}