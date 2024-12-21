import axiosClient from "../axiosClient";

export const fetchAppointments = async (page = 0, size = 10) => {
    try {
        const response = await axiosClient.get(`/admin/appointments?page=${page}&size=${size}`);

        if (response.data && response.data.content && Array.isArray(response.data.content)) {
            return {
                content: response.data.content,
                totalPages: response.data.totalPages,
            };
        } else {
            throw new Error("Dữ liệu không hợp lệ.");
        }
    } catch (error) {
        throw new Error(error.message || "Lỗi khi tải danh sách lịch hẹn.");
    }
};

export const updateAppointmentClinicStatus = async (appointmentId, { clinic_status }) => {
    try {
        const response = await axiosClient.post(
            `/admin/appointments/${appointmentId}/clinic-status`,
            { 
                clinic_status
            }
        );
        return response.data;
    } catch (error) {
        throw new Error(error.message || "Lỗi xảy ra khi cập nhật trạng thái lịch hẹn.");
    }
};

export const searchAppointments = async (searchParams, page = 0, size = 10) => {
    try {
        const { appointmentCode, username } = searchParams;
        const queryParams = new URLSearchParams({
            page: page.toString(),
            size: size.toString(),
            ...(appointmentCode && { appointmentCode }),
            ...(username && { username })
        });

        const response = await axiosClient.get(`/admin/appointments/search?${queryParams}`);

        if (response.data && Array.isArray(response.data.content)) {
            return {
                content: response.data.content,
                totalPages: response.data.totalPages,
            };
        } else {
            throw new Error("Dữ liệu không hợp lệ");
        }
    } catch (error) {
        throw new Error(error.message || "Lỗi khi tìm kiếm lịch hẹn");
    }
};
