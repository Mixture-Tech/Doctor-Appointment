// Format ngày tháng năm
export const formatDate = (dateString) => {
    const date = new Date(dateString);
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();
    return `${day}-${month}-${year}`;
};

// Format giờ
export const formatTime = (timeString) => {
    // Nếu timeString đã có format "HH:mm:ss"
    if (timeString.includes(':')) {
        // Chỉ lấy giờ và phút
        return timeString.substring(0, 5);
    }
    return timeString;
};

// Format khoảng thời gian
export const formatTimeRange = (startTime, endTime) => {
    return `${formatTime(startTime)} - ${formatTime(endTime)}`;
};