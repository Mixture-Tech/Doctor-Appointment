import React, { useState } from "react";
import {useNavigate} from "react-router-dom";

export default function Content() {
    const appointments = [
        { id: 1, date: "20-12-2024", time: "09:00 - 10:00", patient: "Nguyễn Văn A", type: "Khám định kỳ", doctor: "Nguyễn Văn Hùng", },
        { id: 2, date: "21-12-2024", time: "10:00 - 11:00", patient: "Trần Thị B", type: "Tư vấn sức khỏe", doctor: "Trần Quang Tèo" },
        { id: 3, date: "22-12-2024", time: "02:00 - 03:00", patient: "Lê Văn C", type: "Phẫu thuật nhỏ", doctor: "Lê Văn Chí" },
        { id: 4, date: "23-12-2024", time: "11:00 - 12:00", patient: "Nguyễn Thị D", type: "Khám sức khỏe", doctor: "Lê Quang Bình" },
        { id: 5, date: "24-12-2024", time: "14:00 - 15:00", patient: "Lê Quang E", type: "Khám định kỳ", doctor: "Trần Quốc Tế" },
        { id: 6, date: "25-12-2024", time: "08:00 - 09:00", patient: "Trần Thanh G", type: "Tư vấn sức khỏe", doctor: "Nguyễn Văn Tâm" },
        // Thêm nhiều lịch hẹn ở đây nếu cần
    ];

    const navigate = useNavigate();

    const navigateToEdit = () => {
        navigate("/chinh-sua-lich-hen");
    };

    const itemsPerPage = 2; // Số lượng lịch hẹn mỗi trang
    const [currentPage, setCurrentPage] = useState(0); // Trạng thái cho trang hiện tại

    // Tính toán dữ liệu hiển thị cho mỗi trang
    const currentAppointments = appointments.slice(currentPage * itemsPerPage, (currentPage + 1) * itemsPerPage);

    // Hàm xử lý phân trang khi nhấn vào nút
    const handlePaginationClick = (newPage) => {
        if (newPage < 0 || newPage >= Math.ceil(appointments.length / itemsPerPage)) return; // Nếu trang không hợp lệ, không thay đổi
        setCurrentPage(newPage);
    };

    const totalPages = Math.ceil(appointments.length / itemsPerPage); // Tổng số trang

    return (
        <div className="p-6 space-y-6">
            {/* Bảng hiển thị lịch hẹn */}
            <div className="bg-white p-6 rounded-lg shadow">
                <h2 className="text-lg font-semibold mb-4">Danh sách lịch hẹn</h2>
                <table className="table-auto w-full border-collapse border border-gray-300">
                    <thead>
                    <tr className="bg-gray-100">
                        <th className="border border-gray-300 px-4 py-2 font-normal">ID</th>
                        <th className="border border-gray-300 px-4 py-2 font-normal">Ngày</th>
                        <th className="border border-gray-300 px-4 py-2 font-normal">Giờ</th>
                        <th className="border border-gray-300 px-4 py-2 font-normal">Bệnh nhân</th>
                        <th className="border border-gray-300 px-4 py-2 font-normal">Loại</th>
                        <th className="border border-gray-300 px-4 py-2 font-normal">Bác sĩ</th>
                        <th className="border border-gray-300 px-4 py-2 font-normal">Hành động</th>
                    </tr>
                    </thead>
                    <tbody>
                    {currentAppointments.map((appointment) => (
                        <tr key={appointment.id}>
                            <td className="border border-gray-300 px-4 py-2 text-center">{appointment.id}</td>
                            <td className="border border-gray-300 px-4 py-2">{appointment.date}</td>
                            <td className="border border-gray-300 px-4 py-2">{appointment.time}</td>
                            <td className="border border-gray-300 px-4 py-2">{appointment.patient}</td>
                            <td className="border border-gray-300 px-4 py-2">{appointment.type}</td>
                            <td className="border border-gray-300 px-4 py-2">{appointment.doctor}</td>
                            <td className="border border-gray-300 px-4 py-2 text-center">
                                <button className="bg-[#48CFCB] text-white px-3 py-0.5 rounded hover:bg-[#48CFCB]" onClick={navigateToEdit}>
                                    Edit
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>

                {/* Phân trang */}
                <div className="flex justify-center space-x-2 mt-4">
                    {/* Nút "<<": Trước */}
                    <button className={`px-2 py-1 border border-gray-300 rounded ${currentPage === 0 ? "bg-gray-300 text-gray-500" : "hover:bg-[#48CFCB] hover:text-white"}`}
                        onClick={() => handlePaginationClick(currentPage - 1)}
                        disabled={currentPage === 0} // Vô hiệu hóa nút "<<"
                    >
                        &lt;&lt;
                    </button>

                    {/* Các trang */}
                    {[...Array(totalPages)].map((_, index) => (
                        <button
                            key={index}
                            className={`px-3 py-1 border border-gray-300 rounded ${currentPage === index ? "bg-[#48CFCB] text-white" : "hover:bg-[#48CFCB] hover:text-white"}`}
                            onClick={() => handlePaginationClick(index)}
                        >
                            {index + 1}
                        </button>
                    ))}

                    {/* Nút ">>": Sau */}
                    <button
                        className={`px-2 py-1 border border-gray-300 rounded ${currentPage === totalPages - 1 ? "bg-gray-300 text-gray-500" : "hover:bg-[#48CFCB] hover:text-white"}`}
                        onClick={() => handlePaginationClick(currentPage + 1)}
                        disabled={currentPage === totalPages - 1} // Vô hiệu hóa nút ">>"
                    >
                        &gt;&gt;
                    </button>
                </div>
            </div>
        </div>
    );
}
