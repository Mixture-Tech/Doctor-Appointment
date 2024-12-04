import React from 'react';

export default function Right() {
    return (
        <div className="flex flex-col bg-white w-full h-screen">
            {/* Header */}
            <div className="flex items-center justify-between px-6 py-4 border-b border-gray-200">
                <div className="flex items-center space-x-2">
                    <span className="font-semibold text-xl text-gray-800">Chatbot</span>
                </div>
            </div>

            {/* Chat content */}
            <div className="flex-1 overflow-y-auto p-6 space-y-4">
                <div className="bg-gray-50 p-4 rounded-md shadow-md max-w-[800px] mx-auto">
                    <div className="font-semibold text-gray-600 text-right">
                        Hoàng Châu.
                    </div>
                    <hr className="border-t border-gray-300 my-2" />
                    <ol className="mt-2 space-y-2">
                        <li className="text-sm text-gray-600">đau tim là gì?</li>
                    </ol>
                </div>

                <div className="bg-[#D8F7F6] p-4 rounded-md shadow-md max-w-[800px] mx-auto">
                    <div className="font-semibold text-gray-700">Chatbot</div>
                    <hr className="border-t border-gray-300 my-2" />
                    <ol className="mt-2 space-y-2">
                        <li className="text-sm text-gray-600">Đau tim là cảm giác đau hoặc khó chịu ở vùng ngực, có thể do nhiều nguyên nhân khác nhau. Tuy nhiên, khi người ta nói đến "đau tim," thường ý chỉ những cơn đau liên quan đến tim, đặc biệt là khi có vấn đề về lưu thông máu đến tim hoặc khi tim không nhận đủ oxy.</li>
                    </ol>
                </div>

                <div className="bg-gray-50 p-4 rounded-md shadow-md max-w-[800px] mx-auto">
                    <div className="font-semibold text-gray-600 text-right">
                        Hoàng Châu.
                    </div>
                    <hr className="border-t border-gray-300 my-2" />
                    <ol className="mt-2 space-y-2">
                        <li className="text-sm text-gray-600">Thoát vị đĩa đệm là gì?</li>
                    </ol>
                </div>

                <div className="bg-[#D8F7F6] p-4 rounded-md shadow-md max-w-[800px] mx-auto">
                    <div className="font-semibold text-gray-700">Chatbot</div>
                    <hr className="border-t border-gray-300 my-2" />
                    <ol className="mt-2 space-y-2">
                        <li className="text-sm text-gray-600">Thoát vị đĩa đệm là tình trạng mà trong đó một phần của đĩa đệm (một cấu trúc nằm giữa các đốt sống trong cột sống) bị lệch ra ngoài vị trí bình thường của nó. Đĩa đệm có chức năng như một bộ giảm chấn, giúp đệm và hỗ trợ chuyển động giữa các đốt sống. Khi đĩa đệm thoát ra khỏi vị trí, nó có thể gây áp lực lên các dây thần kinh xung quanh, dẫn đến đau, tê, hoặc yếu cơ.</li>
                    </ol>
                </div>

                <div className="bg-gray-50 p-4 rounded-md shadow-md max-w-[800px] mx-auto">
                    <div className="font-semibold text-gray-600 text-right">
                        Hoàng Châu.
                    </div>
                    <hr className="border-t border-gray-300 my-2" />
                    <ol className="mt-2 space-y-2">
                        <li className="text-sm text-gray-600">Cách khắc phục</li>
                    </ol>
                </div>

                <div className="bg-[#D8F7F6] p-4 rounded-md shadow-md max-w-[800px] mx-auto">
                    <div className="font-semibold text-gray-700">Chatbot</div>
                    <hr className="border-t border-gray-300 my-2" />
                    <ol className="mt-2 space-y-2">
                        <li className="text-sm text-gray-600">Khắc phục thoát vị đĩa đệm có thể bao gồm các phương pháp bảo tồn và phẫu thuật tùy vào mức độ nghiêm trọng của tình trạng. Dưới đây là các cách khắc phục phổ biến:

                            1. Điều trị bảo tồn (được áp dụng trong hầu hết các trường hợp thoát vị đĩa đệm nhẹ đến vừa):
                            Nghỉ ngơi: Trong giai đoạn cấp tính, việc nghỉ ngơi ngắn hạn có thể giúp giảm đau và cho cơ thể thời gian phục hồi.
                            Chườm nóng/lạnh: Sử dụng túi chườm lạnh trong vài ngày đầu để giảm viêm và sưng tấy, sau đó có thể chuyển sang chườm ấm để thư giãn cơ bắp.
                            Thuốc giảm đau: Các thuốc như paracetamol, ibuprofen, hoặc thuốc chống viêm không steroid (NSAIDs) có thể giúp giảm đau và viêm.
                            Thuốc giãn cơ: Những thuốc này có thể giúp giảm căng thẳng và co thắt cơ bắp.
                            Vật lý trị liệu: Các bài tập vật lý trị liệu giúp tăng cường cơ bắp hỗ trợ cột sống, cải thiện sự linh hoạt và giảm đau.
                            Thay đổi lối sống: Giảm cân (nếu thừa cân), tránh mang vác vật nặng, và thay đổi tư thế khi ngồi, đứng sẽ giúp giảm áp lực lên cột sống.</li>
                    </ol>
                </div>
            </div>
            <div className="flex w-[900px] items-center border border-gray-300 rounded-lg bg-gray-100 p-7 mx-auto mb-3">
                <textarea
                    placeholder="Tin nhắn Chatbot"
                    className="flex-1 bg-transparent border-none outline-none text-sm placeholder-gray-500 resize-y overflow-y-auto"
                    rows="2" // Đây là số dòng mặc định
                ></textarea>
            </div>


        </div >
    );
}



