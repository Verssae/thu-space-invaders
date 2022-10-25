# Dropship

Requirements : Item System

<br>

## Detail Requirements

1. 아이템 드랍 : 모든 적을 처지했을 때 33% 확률로 랜덤 아이템을 드랍합니다. (Item drop : When all enemies are defeated, there is a 33% chance to drop an random item.)

<br>

2. 아이템 획득 : 처치된 몬스터 위치를 기준으로 직선 아래방향으로 드랍됩니다. 플레이어는 아이템이 맵 밖으로 나가기 전에 획득해야합니다. (How to obtain the item: Drop it in the downward direction based on the location of the defeated monster. Players must acquire items before they leave the map.)

<br>

3. 아이템 효과 (Item Effect)
    1) 공격 속도 : 플레이어의 공격 간격이 0.1초 감소합니다. 효과는 중첩될 수 있습니다. 또한 플레이어 주변에 주황색 공격속도 아이템 이미지가 나타납니다. (Attack Speed : Reduces player attack interval by 0.1 seconds. Effects can be nested. Also, an orange attack speed item image appears around the player.)

    2) 생명 포인트 : 플레이어의 생명이 하나 증가합니다. 효과는 중첩될 수 있습니다. 또한 플레이어 주변에 빨간색 생명 포인트 아이템 이미지가 나타납니다. (Heart Point : The player's life is increased by one. Effects can be stacked. An red life point item image will also appear around the player.)

    3) 이동 속도 : 플레이어의 이동속도가 +1 증가합니다. 효과는 중첩될 수 있습니다. 또한 플레이어 주변에 파란색 이동속도 아이템 이미지가 나타납니다. (Moving Speed : The player's movement speed is increased by +1. Effects can be stacked. Also, an blue movement speed item image appears around the player.)

    4) 총알 3발을 수평또는 수직으로 정렬시켜 발사합니다. 효과는 일회성입니다. (Fires 3 bullets aligned horizontally or vertically. The effect is one-time.)

    5) 총알 5발을 전방으로 발사합니다. 일회성입니다. (Fires 5 bullets forward. It's a one-time thing.)