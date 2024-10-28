# person_pydantic.py
from pydantic import BaseModel

class PersonModel(BaseModel):
    name: str
    age: int

def parse_person(json_str: str):
    person = PersonModel.model_validate_json(json_str)
    return person

if __name__ == "__main__":
    json_str = '{"name": "Alice", "age": 25}'
    person = parse_person(json_str)
    print(person)