import React from 'react';

export default function DiseaseList() {
    return (
        <section className="mb-8">
            <h2 className="text-2xl font-semibold">Bệnh Cơ Xương Khớp</h2>
            <ul className="list-disc list-inside mt-4 space-y-2 text-gray-700 md:text-sm">
                <li>Gout</li>
                <li>Thoái hóa khớp: khớp gối, cột sống thắt lưng, cổ</li>
                <li>Viêm khớp dạng thấp, viêm đa khớp, viêm gân</li>
                <li>Tràn dịch khớp gối, khớp háng, khớp khuỷu</li>
                <li>Loãng xương, đau nhức xương</li>
                <li>Viêm xương, gai xương</li>
                <li>Yếu cơ, loạn dưỡng cơ</li>
                <li>Các chấn thương về cơ, xương, khớp</li>
                <li>...</li>
            </ul>
        </section>
    );
}
