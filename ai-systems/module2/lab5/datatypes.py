from __future__ import annotations
from enum import Enum, IntEnum
from typing import NamedTuple

class Age(Enum):
    YOUNGER = 1 # 18-21
    MIDDLE = 2 # 22-25
    OLDER = 3 # > 26

class Sex(Enum):
    FEMALE = 1
    MALE = 2

class SchoolType(Enum):
    PRIVATE = 1
    STATE = 2
    OTHER = 3

class ScholarshipType(Enum):
    NONE = 1 # 0%
    QUART = 2 # 25%
    HALF = 3 # 50%
    THREEQUART = 4 # 75%
    FULL = 5 # Full

class Salary(Enum):
    UNSPECIFIED = 0
    LOW = 1 # 135-200
    LOWER = 2 # 201-270
    MIDDLE = 3 # 271-340
    HIGHER = 4 # 341-410
    HIGH = 5 # > 410

class Transport(Enum):
    BUS = 1
    CAR = 2 # Includes taxi
    BICYCLE = 3
    OTHER = 4

class Accommodation(Enum):
    RENTAL = 1
    DORMITORY = 2
    FAMILY = 3
    OTHER = 4

class EduLevel(Enum):
    PRIMARY = 1
    SECONDARY = 2
    HIGH = 3
    UNI = 4
    MSC = 5
    PHD = 6

class ParentStatus(Enum):
    MARRIED = 1
    DIVORCED = 2
    DIED = 3

class Occupation(Enum):
    RETIRED = 1
    HOUSEWIFE = 2
    GOVERNMENT = 3
    PRIVATE = 4
    SELF = 5
    OTHER = 6

class StudyHours(Enum): # In a week
    NONE = 1
    LOW = 2 # < 5
    MIDDLE = 3 # 6-10
    HIGH = 4 # 11-20
    HIGHER = 5 # > 20

class Reading(Enum):
    NONE = 1
    SOME = 2
    OFTEN = 3

def decode_bool(s: str) -> bool:
    return s == '1'

class ProjectImpact(Enum):
    POSITIVE = 1
    NEGATIVE = 2
    NEUTRAL = 3

class Attendance(Enum):
    ALWAYS = 1
    SOME = 2
    NEVER = 3

class PreparationGroup(Enum):
    ALONE = 1
    FRIENDS = 2
    NOTAPPLICABLE = 3

class PreparationTime(Enum):
    CLOSESEST = 1
    REGURAL = 2
    NEVER = 3

class Frequency(Enum):
    NEVER = 1
    SOME = 2
    ALWAYS = 3

class FlipClass(Enum):
    NOTUSEFUL = 1
    USEFUL = 2
    NOTAPPLICABLE = 3

class CumulativeGrade(Enum):
    LOWEST = 1 # < 2
    LOW = 2 # 2-2.49
    LOWER = 3 # 2.5-2.99
    MIDDLE = 4 # 3-3.49
    HIGH = 5 # > 3.49

class OutputGrade(IntEnum):
    FAIL = 0
    DD = 1
    DC = 2
    CC = 3
    CB = 4
    BB = 5
    BA = 6
    AA = 7


class Student(NamedTuple):
    age: Age
    sex: Sex
    school_type: SchoolType
    scholarship: ScholarshipType
    add_work: bool
    activities: bool
    partner: bool
    salary: Salary
    transport: Transport
    accommodation: Accommodation
    mother_edu: EduLevel
    father_edu: EduLevel
    siblings: int
    parent_status: ParentStatus
    mother_job: Occupation
    father_job: Occupation
    study_hrs: StudyHours
    reading_nonscience: Reading
    reading_science: Reading
    conferences: bool
    project_impact: ProjectImpact
    attendance: Attendance
    prep_group: PreparationGroup
    prep_time: PreparationTime
    notes: Frequency
    class_listen: Frequency
    discussion_improve: Frequency
    flip_class: FlipClass
    grade_avg: CumulativeGrade
    expected_grade_avg: CumulativeGrade
    course_id: int
    out_grade: OutputGrade
    student_id: int

    @staticmethod
    def from_row(row: list[str]) -> Student:
        return Student(
            student_id=int(row[0][len('STUDENT'):]),
            age=Age(int(row[1])),
            sex=Sex(int(row[2])),
            school_type=SchoolType(int(row[3])),
            scholarship=ScholarshipType(int(row[4])),
            add_work=row[5] == '1',
            activities=row[6] == '1',
            partner=row[7] == '1',
            salary=Salary(int(row[8])),
            transport=Transport(int(row[9])),
            accommodation=Accommodation(int(row[10])),
            mother_edu=EduLevel(int(row[11])),
            father_edu=EduLevel(int(row[12])),
            siblings=int(row[13]),
            parent_status=ParentStatus(int(row[14])),
            mother_job=Occupation(int(row[15])),
            father_job=Occupation(int(row[16])),
            study_hrs=StudyHours(int(row[17])),
            reading_nonscience=Reading(int(row[18])),
            reading_science=Reading(int(row[19])),
            conferences=row[20] == '1',
            project_impact=ProjectImpact(int(row[21])),
            attendance=Attendance(int(row[22])),
            prep_group=PreparationGroup(int(row[23])),
            prep_time=PreparationTime(int(row[24])),
            notes=Frequency(int(row[25])),
            class_listen=Frequency(int(row[26])),
            discussion_improve=Frequency(int(row[27])),
            flip_class=FlipClass(int(row[28])),
            grade_avg=CumulativeGrade(int(row[29])),
            expected_grade_avg=CumulativeGrade(int(row[30])),
            course_id=int(row[31]),
            out_grade=OutputGrade(int(row[32])),
        )
