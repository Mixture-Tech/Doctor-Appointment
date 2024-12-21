import React, { useState, useEffect } from "react";
import { useParams, useNavigate, useLocation } from "react-router-dom";
import { updateAppointmentClinicStatus } from "../../../../services/apis/admin/appointment-management";
import { formatDate, formatTimeRange } from "../../../../utils/date-time-formatter";
import { toast } from "react-toastify";

export default function EditAppointment() {
    const { appointmentId } = useParams();
    const navigate = useNavigate();
    const location = useLocation();
    const [appointment, setAppointment] = useState(null);
    const [selectedStatus, setSelectedStatus] = useState(""); // Thêm state cho status đang chọn
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [saving, setSaving] = useState(false);

    // Danh sách trạng thái clinic có thể chọn
    const clinicStatuses = [
        { value: "PENDING", label: "Chờ khám" },
        { value: "COMPLETED", label: "Đã khám" },
        { value: "NO_SHOW", label: "Không có mặt" }
    ];

    useEffect(() => {
        const appointmentData = location.state?.appointment;
        if (appointmentData) {
            setAppointment(appointmentData);
            setSelectedStatus(appointmentData.clinic_status); // Set giá trị status ban đầu
        } else {
            setError("Không tìm thấy thông tin lịch hẹn");
        }
    }, [appointmentId, location]);

    const handleStatusChange = (event) => {
        const newStatus = event.target.value;
        if (appointment.clinic_status === "COMPLETED") {
            toast.error("Trạng thái 'Đã khám' không thể thay đổi!");
            return;
        }
        if (appointment.clinic_status === "NO_SHOW") {
            toast.error("Trạng thái 'Không có mặt' không thể thay đổi!");
            return;
        }
        setSelectedStatus(newStatus);
    };

    const handleSave = async () => {
        if (appointment.clinic_status === "COMPLETED") {
            toast.error("Không thể thay đổi trạng thái khi đã là 'Đã khám'!");
            return;
        }

        if (appointment.clinic_status === "NO_SHOW") {
            toast.error("Không thể thay đổi trạng thái khi đã là 'Không có mặt'!");
            return;
        }

        if (selectedStatus === appointment.clinic_status) {
            toast.error("Không có thay đổi nào được thực hiện!");
            return;
        }

        try {
            setSaving(true);
            await updateAppointmentClinicStatus(appointmentId, { clinic_status: selectedStatus });
            setAppointment(prev => ({
                ...prev,
                clinic_status: selectedStatus
            }));
            toast.success("Cập nhật trạng thái thành công!");
            navigate("/admin/quan-ly-lich-hen");
        } catch (err) {
            toast.error(err.message);
            setError(err.message);
        } finally {
            setSaving(false);
        }
    };

    const handleCancel = () => {
        navigate("/admin/quan-ly-lich-hen");
    };

    if (loading) {
        return <div className="p-6">Đang tải dữ liệu...</div>;
    }

    if (!appointment) {
        return <div className="p-6">Không tìm thấy thông tin lịch hẹn</div>;
    }

    return (
        <div className="px-4 space-y-4 max-w-2xl mx-auto">
            <div className="bg-white p-6 rounded-lg shadow-2xl">
                <h2 className="text-xl font-semibold mb-6">Chi tiết lịch hẹn</h2>

                <div className="space-y-4">
                    {/* Mã lịch hẹn */}
                    <div className="flex items-center space-x-3">
                        <label className="w-1/4 text-gray-700 font-semibold">Mã lịch hẹn</label>
                        <div className="w-3/4 p-2 bg-gray-200 rounded-md">
                            {appointment.appointment_code}
                        </div>
                    </div>

                    {/* Ngày khám */}
                    <div className="flex items-center space-x-3">
                        <label className="w-1/4 text-gray-700 font-semibold">Ngày khám</label>
                        <div className="w-3/4 p-2 bg-gray-200 rounded-md">
                            {formatDate(appointment.taken_date)}
                        </div>
                    </div>

                    {/* Giờ khám */}
                    <div className="flex items-center space-x-3">
                        <label className="w-1/4 text-gray-700 font-semibold">Giờ khám</label>
                        <div className="w-3/4 p-2 bg-gray-200 rounded-md">
                            {formatTimeRange(appointment.start_time, appointment.end_time)}
                        </div>
                    </div>

                    {/* Thông tin bệnh nhân */}
                    <div className="flex items-center space-x-3">
                        <label className="w-1/4 text-gray-700 font-semibold">Bệnh nhân</label>
                        <div className="w-3/4 p-2 bg-gray-200 rounded-md">
                            {appointment.username}
                        </div>
                    </div>

                    {/* Số điện thoại */}
                    <div className="flex items-center space-x-3">
                        <label className="w-1/4 text-gray-700 font-semibold">Số điện thoại</label>
                        <div className="w-3/4 p-2 bg-gray-200 rounded-md">
                            {appointment.phone}
                        </div>
                    </div>

                    {/* Địa chỉ */}
                    <div className="flex items-center space-x-3">
                        <label className="w-1/4 text-gray-700 font-semibold">Địa chỉ</label>
                        <div className="w-3/4 p-2 bg-gray-200 rounded-md">
                            {appointment.address}
                        </div>
                    </div>

                    {/* Bác sĩ */}
                    <div className="flex items-center space-x-3">
                        <label className="w-1/4 text-gray-700 font-semibold">Bác sĩ</label>
                        <div className="w-3/4 p-2 bg-gray-200 rounded-md">
                            {appointment.doctor_name}
                        </div>
                    </div>

                    {/* Bác sĩ */}
                    <div className="flex items-center space-x-3">
                        <label className="w-1/4 text-gray-700 font-semibold">Tạo lịch</label>
                        <div className="w-3/4 p-2 bg-gray-200 rounded-md">
                            {appointment.created_at}
                        </div>
                    </div>
                    
                    {/* Bác sĩ */}
                    <div className="flex items-center space-x-3">
                        <label className="w-1/4 text-gray-700 font-semibold">Cập nhật lịch</label>
                        <div className="w-3/4 p-2 bg-gray-200 rounded-md">
                            {appointment.updatedAt}
                        </div>
                    </div>

                    

                    {/* Trạng thái khám - Có thể chỉnh sửa */}
                    <div className="flex items-center space-x-3">
                        <label className="w-1/4 text-gray-700 font-semibold">Trạng thái khám</label>
                        <select
                            value={selectedStatus}
                            onChange={handleStatusChange}
                            disabled={saving}
                            className="w-3/4 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary-500"
                        >
                            {clinicStatuses.map(status => (
                                <option key={status.value} value={status.value}>
                                    {status.label}
                                </option>
                            ))}
                        </select>
                    </div>
                </div>

                {/* Buttons */}
                <div className="mt-6 flex justify-end space-x-4">
                    <button
                        onClick={handleCancel}
                        className="px-4 py-2 bg-gray-100 text-gray-700 rounded-md hover:bg-gray-200 transition-colors"
                        disabled={saving}
                    >
                        Quay lại
                    </button>
                    <button
                        onClick={handleSave}
                        disabled={saving || selectedStatus === appointment.clinic_status}
                        className={`px-4 py-2 bg-[#48CFCB] text-white rounded-md hover:bg-[#3BA7A4] transition-colors ${
                            (saving || selectedStatus === appointment.clinic_status) ? 'opacity-200 cursor-not-allowed' : ''
                        }`}
                    >
                        {saving ? 'Đang lưu...' : 'Lưu'}
                    </button>
                </div>
            </div>
        </div>
    );
}